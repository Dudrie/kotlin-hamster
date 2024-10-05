package de.github.dudrie.hamster.core.game

import de.github.dudrie.hamster.core.exception.SpielException
import de.github.dudrie.hamster.core.file.SpielImporter
import de.github.dudrie.hamster.core.game.commands.Kommando
import de.github.dudrie.hamster.core.game.commands.SpawneHamsterKommando
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.Kachel
import de.github.dudrie.hamster.core.model.kachel.KornInhalt
import de.github.dudrie.hamster.core.model.kachel.Leer
import de.github.dudrie.hamster.core.model.kachel.Wand
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.core.model.util.Richtung
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Semaphore

/**
 * Verwaltet alle Informationen, die zum Ablauf des Spiels notwendig sind.
 */
class SpielViewModel {

    /**
     * Backing field von [spielZustand].
     */
    private val _spielZustand = MutableStateFlow(SpielZustand())

    /**
     * Gibt den aktuellen [SpielZustand] zurück.
     */
    val spielZustand = _spielZustand.asStateFlow()

    /**
     * Backing field von [ladtSpiel].
     */
    private val _ladtSpiel = MutableStateFlow(true)

    /**
     * Wird gerade ein Territorium geladen?
     */
    val ladtSpiel = _ladtSpiel.asStateFlow()

    /**
     * Das aktuelle [InternesTerritorium] des [spielZustand].
     *
     * @throws NoSuchElementException Im [spielZustand] befindet sich kein Territorium.
     */
    val territorium: InternesTerritorium
        get() = _spielZustand.value.aktuellesTerritorium
            ?: throw NoSuchElementException("ERR_NO_TERRITORY")

    /**
     * Der "Standardhamster" des [spielZustand].
     *
     * _Als Standardhamster wird der erste Hamster in der [Hamsterliste][InternesTerritorium.hamster] bezeichnet._
     */
    val standardHamster: InternerHamster
        get() = _spielZustand.value.aktuellesTerritorium?.hamster?.first()
            ?: throw NoSuchElementException("ERR_NO_DEFAULT_HAMSTER")

    /**
     * Die Geschwindigkeit, in der die Simulation ablaufen soll.
     */
    private val geschwindigkeit: Double
        get() = spielZustand.value.geschwindigkeit

    /**
     * Der aktuelle [SpielModus] des [spielZustand].
     */
    private val spielModus: SpielModus
        get() = spielZustand.value.modus

    /**
     * Das [SpielLog] der aktuellen Simulation.
     */
    val spielLog = SpielLog()

    /**
     * Wird dafür benutzt, dass die Kommandos hintereinander ausgeführt werden.
     */
    private val kommandoLock = Semaphore(1)

    /**
     * Setzt die Geschwindigkeit der Simulation auf die übergebene [geschwindigkeit].
     */
    fun setGeschwindigkeit(geschwindigkeit: Double) {
        require(geschwindigkeit in SpielDefaults.MIN_GESCHWINDIGKEIT..SpielDefaults.MAX_GESCHWINDIGKEIT) { "Die Geschwindigkeit muss zwischen 1.0 und 10.0 liegen." }
        _spielZustand.update { it.copy(geschwindigkeit = geschwindigkeit) }
    }

    /**
     * Führt das übergebene [kommando] aus.
     *
     * Sollte es dabei zu einer [SpielException] kommen, wird das Spiel [abgebrochen][SpielModus.Abgebrochen].
     */
    suspend fun fuhreAus(kommando: Kommando) {
        kommandoLock.acquire()
        try {
            requireKommandoAusfuhrbar(kommando)
            val altesTerritorium = _spielZustand.value.aktuellesTerritorium
            require(altesTerritorium != null) { "ERR_NO_TERRITORY" }

            val neuesTerritorium = kommando.fuhreAus(altesTerritorium)
            val ergebnis = KommandoErgebnis(kommando, altesTerritorium, neuesTerritorium)

            _spielZustand.update {
                it.copy(
                    aktuellesTerritorium = neuesTerritorium,
                    ausgefuhrteKommandos = it.ausgefuhrteKommandos + ergebnis
                )
            }

            spielLog.fugeNachrichtHinzu(kommando.getLogNachricht())
            delay(((SpielDefaults.MAX_GESCHWINDIGKEIT + 1 - geschwindigkeit) / 5.0 * 400.0).toLong())
        } catch (e: SpielException) {
            brichSpielAb(e)
        } finally {
            kommandoLock.release()
        }
    }

    /**
     * Startet das Spiel und setzt es auf den Startpunkt zurück.
     *
     * Ist [startePausiert] `true`, so wird das Spiel direkt [pausiert][SpielModus.Pausiert]. Ansonsten [läuft][SpielModus.Lauft] es.
     */
    suspend fun starteSpiel(startePausiert: Boolean) {
        require(spielModus == SpielModus.Initialisierung) { "ERR_GAME_NOT_INITIALIZING" }
        require(spielZustand.value.aktuellesTerritorium != null) { "ERR_NO_TERRITORY_LOADED" }

        _spielZustand.update {
            it.copy(
                aktuellesTerritorium = it.aktuellesTerritorium,
                modus = if (startePausiert) SpielModus.Pausiert else SpielModus.Lauft,
                geschwindigkeit = it.geschwindigkeit,
                fehler = null,
                ausgefuhrteKommandos = listOf(),
                wiederherstellbareKommandos = listOf()
            )
        }

        if (startePausiert) {
            kommandoLock.acquire()
        }
    }

    /**
     * [Pausiert][SpielModus.Pausiert] das Spiel.
     *
     * Es kann mit [setzeSpielFort] fortgesetzt werden.
     */
    suspend fun pausiereSpiel() {
        require(spielModus == SpielModus.Lauft) { "ERR_GAME_NOT_RUNNING" }
        kommandoLock.acquire()
        _spielZustand.update { it.copy(modus = SpielModus.Pausiert) }
    }

    /**
     * Setzt ein pausiertes Spiel fort.
     *
     * Es [läuft][SpielModus.Lauft] anschließend wieder.
     */
    fun setzeSpielFort() {
        require(spielModus == SpielModus.Pausiert) { "ERR_GAME_NOT_PAUSED" }
        stelleAlleWiederHer()
        _spielZustand.update { it.copy(modus = SpielModus.Lauft) }
        kommandoLock.release()
    }

    /**
     * Macht das letzte ausgeführte Kommando rückgängig.
     */
    fun ruckgangig() {
        requireKannRuckganigOderWiederherstellen()
        require(_spielZustand.value.ausgefuhrteKommandos.isNotEmpty()) { "ERR_NO_COMMAND_TO_UNDO" }

        val letztesKommando = _spielZustand.value.ausgefuhrteKommandos.last()
        val zukunftigeKommandos = _spielZustand.value.wiederherstellbareKommandos.toMutableList()
        zukunftigeKommandos.add(0, letztesKommando)

        _spielZustand.update {
            it.copy(
                aktuellesTerritorium = letztesKommando.territoriumVorher,
                ausgefuhrteKommandos = it.ausgefuhrteKommandos - letztesKommando,
                wiederherstellbareKommandos = zukunftigeKommandos
            )
        }

        spielLog.zeigeEineNachrichtWeniger()
    }

    /**
     * Stellt das zuletzt rückgängig gemachte Kommando wieder her.
     */
    fun stelleWiederHer() {
        requireKannRuckganigOderWiederherstellen()
        require(_spielZustand.value.wiederherstellbareKommandos.isNotEmpty()) { "ERR_NO_COMMAND_TO_REDO" }

        val nachstesKommando = _spielZustand.value.wiederherstellbareKommandos.first()

        _spielZustand.update {
            it.copy(
                aktuellesTerritorium = nachstesKommando.territoriumNachher,
                ausgefuhrteKommandos = it.ausgefuhrteKommandos + nachstesKommando,
                wiederherstellbareKommandos = it.wiederherstellbareKommandos - nachstesKommando
            )
        }

        spielLog.zeigeEineNachrichtMehr()
    }

    /**
     * Stellt **alle** rückgängig gemachten Kommandos wieder her.
     */
    fun stelleAlleWiederHer() {
        requireKannRuckganigOderWiederherstellen()
        val wiederherstellbareKommandos = _spielZustand.value.wiederherstellbareKommandos

        if (wiederherstellbareKommandos.isNotEmpty()) {
            val letztesKommando = wiederherstellbareKommandos.last()
            _spielZustand.update {
                it.copy(
                    aktuellesTerritorium = letztesKommando.territoriumNachher,
                    ausgefuhrteKommandos = it.ausgefuhrteKommandos + it.wiederherstellbareKommandos,
                    wiederherstellbareKommandos = listOf()
                )
            }
            spielLog.zeigeAlleNachrichten()
        }
    }

    /**
     * [Stoppt][SpielModus.Gestoppt] das Spiel.
     */
    suspend fun stoppeSpiel() {
        require(spielModus == SpielModus.Lauft) { "ERR_GAME_NOT_RUNNING" }
        _spielZustand.update { it.copy(modus = SpielModus.Gestoppt) }
        kommandoLock.acquire()
    }

    /**
     * [Bricht][SpielModus.Abgebrochen] mit dem übergebenen [fehler] ab.
     */
    private suspend fun brichSpielAb(fehler: SpielException) {
        require(spielModus == SpielModus.Lauft) { "ERR_GAME_NOT_RUNNING" }
        _spielZustand.update {
            it.copy(
                modus = SpielModus.Abgebrochen,
                fehler = fehler
            )
        }
        kommandoLock.acquire()
    }

    /**
     * Lädt ein Spiel aus der [territoriumsDatei].
     *
     * Wird keine [territoriumsDatei] angegeben, so wird das [Standardterritorium][SpielImporter.STANDARD_DATEIPFAD] geladen.
     *
     * @throws RuntimeException Das Spiel befindet sich nicht im Modus [SpielModus.Initialisierung].
     */
    suspend fun ladeSpiel(territoriumsDatei: String?) {
        require(spielModus == SpielModus.Initialisierung) { "ERR_GAME_NOT_INITIALIZING" }
        kommandoLock.acquire()
        _ladtSpiel.update { true }

        val territorium = SpielImporter.ladeTerritoriumAusProjekt(
            dateipfad = territoriumsDatei ?: SpielImporter.STANDARD_DATEIPFAD,
            ausResourceOrdner = territoriumsDatei == null
        )

        _spielZustand.update { it.copy(aktuellesTerritorium = territorium) }
        _ladtSpiel.update { false }
        kommandoLock.release()
    }

    /**
     * Stellt sicher, dass der [spielModus] eine Ausführung des [kommando] zulässt.
     *
     * @throws IllegalStateException Der [spielModus] lässt eine Ausführung des [kommando] nicht zu.
     */
    private fun requireKommandoAusfuhrbar(kommando: Kommando) {
        when (spielModus) {
            SpielModus.Initialisierung -> if (kommando !is SpawneHamsterKommando) {
                throw IllegalStateException("ERR_GAME_NOT_RUNNING")
            }

            else -> return
        }
    }

    /**
     * Stellt sicher, dass der aktuelle [spielModus] das Rückgängigmachen bzw. Wiederherstellen von Kommandos erlaubt.
     *
     * @throws RuntimeException Der [spielModus] lässt ein Rückgängigmachen bzw. Wiederherstellen nicht zu.
     */
    private fun requireKannRuckganigOderWiederherstellen() {
        require(
            spielModus == SpielModus.Pausiert
                    || spielModus == SpielModus.Gestoppt
                    || spielModus == SpielModus.Abgebrochen
        ) { "ERR_CAN_NOT_UNDO_OR_REDO" }
    }

    /**
     * Erstellt das Standardterritorium des Hamsterspiels.
     */
    fun erstelleStandardTerritorium() {
        require(spielModus == SpielModus.Initialisierung) { "ERR_GAME_NOT_INITIALIZING" }
        val kacheln = mutableMapOf<Position, Kachel>()

        repeat(5) {
            kacheln[Position(it, 0)] = Kachel(Wand)
            kacheln[Position(it, 2)] = Kachel(Wand)
        }

        kacheln[Position(0, 1)] = Kachel(Wand)
        kacheln[Position(1, 1)] = Kachel(Leer)
        kacheln[Position(2, 1)] = Kachel(Leer)
        kacheln[Position(3, 1)] = Kachel(KornInhalt(3))
        kacheln[Position(4, 1)] = Kachel(Wand)

        val hamster = InternerHamster(
            position = Position(1, 1),
            richtung = Richtung.Osten,
            inventar = listOf()
        )

        val territorium = InternesTerritorium(hamster = listOf(hamster), kacheln = kacheln)

        _spielZustand.update { it.copy(aktuellesTerritorium = territorium) }
    }
}
