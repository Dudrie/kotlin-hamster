package de.github.dudrie.hamster.core.game

import de.github.dudrie.hamster.core.exception.SpielException
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.HamsterString
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Semaphore

class SpielViewModel {

    private val _spielZustand = MutableStateFlow(SpielZustand())
    val spielZustand = _spielZustand.asStateFlow()

    private val geschwindigkeit: Double
        get() = spielZustand.value.geschwindigkeit

    private val spielModus: SpielModus
        get() = spielZustand.value.modus

    private val spielLog = SpielLog()

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

        _spielZustand.update {
            SpielZustand().copy(
                modus = if (startePausiert) SpielModus.Pausiert else SpielModus.Lauft,
                geschwindigkeit = it.geschwindigkeit
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
        kommandoLock.acquire()
        _spielZustand.update { it.copy(modus = SpielModus.Gestoppt) }
    }

    suspend fun brichSpielAb(fehler: SpielException) {
        require(spielModus == SpielModus.Lauft) { "ERR_GAME_NOT_RUNNING" }
        kommandoLock.acquire()
        _spielZustand.update {
            it.copy(
                modus = SpielModus.Abgebrochen,
                fehler = fehler
            )
        }
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
}

class SpielLog {
    private val _nachrichten = MutableStateFlow(listOf<HamsterString>())
    val nachrichten = _nachrichten.asStateFlow()

    fun zeigeNachricht(nachricht: HamsterString) {
        _nachrichten.update { it + nachricht }
    }

    fun zeigeMehrereNachrichten(nachrichten: List<HamsterString>) {
        _nachrichten.update { it + nachrichten }
    }

    fun entferneLetzteNachricht() {
        _nachrichten.update {
            val neueListe = it.toMutableList()
            neueListe.removeLast()
            neueListe
        }
    }
}

data class KommandoErgebnis(
    val kommando: Kommando,
    val territoriumVorher: InternesTerritorium,
    val territoriumNachher: InternesTerritorium
)

data class SpielZustand(
    val aktuellesTerritorium: InternesTerritorium? = null,
    val geschwindigkeit: Double = 4.0,
    val modus: SpielModus = SpielModus.Initialisierung,
    val fehler: SpielException? = null,
    val ausgefuhrteKommandos: List<KommandoErgebnis> = listOf(),
    /**
     * Liste aller Kommandos, die rückgängig gemacht wurden.
     */
    val wiederherstellbareKommandos: List<KommandoErgebnis> = listOf()
)
