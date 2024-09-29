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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Semaphore
import kotlin.reflect.KProperty

class SpielViewModel {

    private val _spielZustand = MutableStateFlow(SpielZustand())
    val spielZustand = _spielZustand.asStateFlow()

    private val _ladtSpiel = MutableStateFlow(true)
    val ladtSpiel = _ladtSpiel.asStateFlow()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): StateFlow<SpielZustand> {
        return spielZustand
    }

    val territorium: InternesTerritorium
        get() = _spielZustand.value.aktuellesTerritorium
            ?: throw NoSuchElementException("ERR_NO_TERRITORY")

    val standardHamster: InternerHamster
        get() = _spielZustand.value.aktuellesTerritorium?.hamster?.first()
            ?: throw NoSuchElementException("ERR_NO_DEFAULT_HAMSTER")

    private val geschwindigkeit: Double
        get() = spielZustand.value.geschwindigkeit

    private val spielModus: SpielModus
        get() = spielZustand.value.modus

    val spielLog = SpielLog()

    private val kommandoLock = Semaphore(1)

    fun setGeschwindigkeit(geschwindigkeit: Double) {
        _spielZustand.update { it.copy(geschwindigkeit = geschwindigkeit) }
    }

    fun setStartTerritorium(territorium: InternesTerritorium) {
        require(spielModus == SpielModus.Initialisierung) { "ERR_GAME_NOT_INITIALIZING" }
        _spielZustand.update {
            SpielZustand().copy(
                aktuellesTerritorium = territorium,
                geschwindigkeit = it.geschwindigkeit
            )
        }
    }

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

            spielLog.zeigeNachricht(kommando.getLogNachricht())
            delay(((SpielDefaults.MAX_GESCHWINDIGKEIT + 1 - geschwindigkeit) / 5.0 * 400.0).toLong())
        } catch (e: SpielException) {
            brichSpielAb(e)
        } finally {
            kommandoLock.release()
        }
    }

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

    suspend fun pausiereSpiel() {
        require(spielModus == SpielModus.Lauft) { "ERR_GAME_NOT_RUNNING" }
        kommandoLock.acquire()
        _spielZustand.update { it.copy(modus = SpielModus.Pausiert) }
    }

    fun setzeSpielFort() {
        require(spielModus == SpielModus.Pausiert) { "ERR_GAME_NOT_PAUSED" }
        stelleAlleWiederHer()
        _spielZustand.update { it.copy(modus = SpielModus.Lauft) }
        kommandoLock.release()
    }

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

        spielLog.entferneLetzteNachricht()
    }

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

        spielLog.zeigeNachricht(nachstesKommando.kommando.getLogNachricht())
    }

    private fun stelleAlleWiederHer() {
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

            spielLog.zeigeMehrereNachrichten(wiederherstellbareKommandos.map { it.kommando.getLogNachricht() })
        }
    }

    suspend fun stoppeSpiel() {
        require(spielModus == SpielModus.Lauft) { "ERR_GAME_NOT_RUNNING" }
        _spielZustand.update { it.copy(modus = SpielModus.Gestoppt) }
        kommandoLock.acquire()
    }

    suspend fun brichSpielAb(fehler: SpielException) {
        require(spielModus == SpielModus.Lauft) { "ERR_GAME_NOT_RUNNING" }
        _spielZustand.update {
            it.copy(
                modus = SpielModus.Abgebrochen,
                fehler = fehler
            )
        }
        kommandoLock.acquire()
    }


    suspend fun ladeSpiel(territoriumsDatei: String?) {
        require(spielModus == SpielModus.Initialisierung) { "ERR_GAME_NOT_INITIALIZING" }
        kommandoLock.acquire()
        _ladtSpiel.update { true }

        val territorium = SpielImporter.getStartTerritorium(
            dateipfad = territoriumsDatei ?: SpielImporter.STANDARD_DATEIPFAD,
            ausResourceOrdner = territoriumsDatei == null
        )

        _spielZustand.update { it.copy(aktuellesTerritorium = territorium) }
        _ladtSpiel.update { false }
        kommandoLock.release()
    }

    private fun requireKommandoAusfuhrbar(kommando: Kommando) {
        when (spielModus) {
            SpielModus.Initialisierung -> if (kommando !is SpawneHamsterKommando) {
                throw IllegalStateException("ERR_GAME_NOT_RUNNING")
            }

            else -> return
        }
    }

    private fun requireKannRuckganigOderWiederherstellen() {
        require(
            spielModus == SpielModus.Pausiert
                    || spielModus == SpielModus.Gestoppt
                    || spielModus == SpielModus.Abgebrochen
        ) { "ERR_CAN_NOT_UNDO_OR_REDO" }
    }

    // TODO: Remove me
    fun erstelleStandardTerritorium() {
        require(spielModus == SpielModus.Initialisierung) { "ERR_GAME_NOT_INITIALIZING" }
        val kacheln = mutableMapOf<Position, Kachel>()

        repeat(5) {
            kacheln[Position(it, 0)] = Kachel(Wand())
            kacheln[Position(it, 2)] = Kachel(Wand())
        }

        kacheln[Position(0, 1)] = Kachel(Wand())
        kacheln[Position(1, 1)] = Kachel(Leer)
        kacheln[Position(2, 1)] = Kachel(Leer)
        kacheln[Position(3, 1)] = Kachel(KornInhalt(3))
        kacheln[Position(4, 1)] = Kachel(Wand())

        val hamster = InternerHamster(
            position = Position(1, 1),
            richtung = Richtung.Osten,
            inventar = listOf()
        )

        val territorium = InternesTerritorium(hamster = listOf(hamster), kacheln = kacheln)

        _spielZustand.update { it.copy(aktuellesTerritorium = territorium) }
    }
}
