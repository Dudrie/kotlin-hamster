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

    companion object {
        const val minGeschwindigkeit = 1.0

        const val maxGeschwindigkeit = 10.0

        const val schritteGeschwindigkeit = (maxGeschwindigkeit - minGeschwindigkeit - 1).toInt()

        val geschwindigkeitInterval = minGeschwindigkeit..maxGeschwindigkeit
    }

    private val _spielZustand = MutableStateFlow(SpielZustand())
    val spielZustand = _spielZustand.asStateFlow()

    private val geschwindigkeit: Double
        get() = spielZustand.value.geschwindigkeit

    private val spielModus: SpielModus
        get() = spielZustand.value.modus

    private val spielLog = SpielLog()

    private val kommandoLock = Semaphore(1)
    private val pausiertLock = Semaphore(1)

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

    fun setzeStartHamster(kommando: SpawneHamsterKommando) {

    }

    suspend fun fuhreAus(kommando: Kommando) {
        pausiertLock.acquire()
        kommandoLock.acquire()
        try {
            requireKommandoAusfuhrbar(kommando)
            val aktuellesTerritorium = _spielZustand.value.aktuellesTerritorium
            require(aktuellesTerritorium != null) { "ERR_NO_TERRITORY" }

            val neuesTerritorium = kommando.fuhreAus(aktuellesTerritorium)
            val ergebnis = KommandoErgebnis(kommando, neuesTerritorium)

            _spielZustand.update {
                it.copy(
                    aktuellesTerritorium = neuesTerritorium,
                    ausgefuhrteKommandos = it.ausgefuhrteKommandos + ergebnis
                )
            }
            spielLog.zeigeNachricht(kommando.getLogNachricht())

            delay(((maxGeschwindigkeit + 1 - geschwindigkeit) / 5.0 * 400.0).toLong())
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
            pausiertLock.acquire()
        }
    }

    suspend fun pausiereSpiel() {
        require(spielModus == SpielModus.Lauft) { "ERR_GAME_NOT_RUNNING" }
        pausiertLock.acquire()
        _spielZustand.update { it.copy(modus = SpielModus.Pausiert) }
    }

    fun setzeSpielFort() {
        require(spielModus == SpielModus.Pausiert) { "ERR_GAME_NOT_PAUSED" }
        stelleAlleWiederHer()
        _spielZustand.update { it.copy(modus = SpielModus.Lauft) }
        pausiertLock.release()
    }

    suspend fun stoppeSpiel() {
        require(spielModus == SpielModus.Lauft) { "ERR_GAME_NOT_RUNNING" }
        pausiertLock.acquire()
        _spielZustand.update { it.copy(modus = SpielModus.Gestoppt) }
    }

    suspend fun brichSpielAb(fehler: SpielException) {
        require(spielModus == SpielModus.Lauft) { "ERR_GAME_NOT_RUNNING" }
        pausiertLock.acquire()
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
}

class SpielLog {
    private val _nachrichten = MutableStateFlow(listOf<HamsterString>())
    val nachrichten = _nachrichten.asStateFlow()

    fun zeigeNachricht(nachricht: HamsterString) {
        _nachrichten.update { it + nachricht }
    }

    fun entferneLetzteNachricht() {
        _nachrichten.update {
            val neueListe = it.toMutableList()
            neueListe.removeLast()
            neueListe
        }
    }
}

data class KommandoErgebnis(val kommando: Kommando, val territoriumVorher: InternesTerritorium)

data class SpielZustand(
    val aktuellesTerritorium: InternesTerritorium? = null,
    val geschwindigkeit: Double = 4.0,
    val modus: SpielModus = SpielModus.Initialisierung,
    val fehler: SpielException? = null,
    val ausgefuhrteKommandos: List<KommandoErgebnis> = listOf(),
    /**
     * Liste aller Kommandos, die rückgängig gemacht wurden.
     */
    val zukunftigeKommandos: List<KommandoErgebnis> = listOf()
) {
    val kannRuckgangigMachen: Boolean = ausgefuhrteKommandos.isNotEmpty()

    val kannWiederherstellen: Boolean = zukunftigeKommandos.isNotEmpty()
}
