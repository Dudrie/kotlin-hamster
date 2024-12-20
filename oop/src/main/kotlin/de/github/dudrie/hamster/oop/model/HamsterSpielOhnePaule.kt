package de.github.dudrie.hamster.oop.model

import de.github.dudrie.hamster.core.game.SpielViewModel
import de.github.dudrie.hamster.ui.windows.SpielFenster
import kotlinx.coroutines.runBlocking

/**
 * Grundklasse, um ein Spiel zu laden und zu starten.
 *
 * @param territoriumsDatei Pfad zur Territoriumsdatei, die geladen werden soll. Es wird ein Standardterritorium geladen, wenn weggelassen.
 */
abstract class HamsterSpielOhnePaule(private val territoriumsDatei: String? = null) {

    /**
     * Lädt das Spiel mit dem gegebenen [spielName] und der entsprechenden [spielNr].
     *
     * Das Territorium muss sich im `/territories` Ordner befinden.
     *
     * @param spielName Name des Hamsterspiels, welches geladen werden soll.
     * @param spielNr Nummer des Spiels.
     */
    constructor(spielName: String, spielNr: Int) : this("territories/${spielName}_$spielNr.json")

    /**
     * Daten des aktuell geladenen Spiels.
     */
    protected val spiel = SpielViewModel()

    /**
     * Enthält das [Territorium] des Spiels.
     */
    lateinit var territorium: Territorium
        protected set

    /**
     * Das [SpielFenster], welches zu diesem Spiel gehört.
     */
    private var fenster: SpielFenster = SpielFenster(spiel) {
        spiel.ladeSpiel(territoriumsDatei)

        territorium = Territorium(spiel, spiel.territorium)
    }

    init {
        runBlocking { fenster.starte() }
    }

    /**
     * Wird von [lasseSpielAblaufen] aufgerufen, sodass der Code für das Spiel ausgeführt wird.
     *
     * Das Spiel muss **nicht** von Hand gestartet oder gestoppt werden.
     */
    protected abstract fun fuehreAus()

    /**
     * Lässt das in [fuehreAus] Spiel ablaufen.
     *
     * Davor wird das Spiel gestartet und anschließend gestoppt.
     */
    fun lasseSpielAblaufen() {
        runBlocking {
            spiel.starteSpiel(false)
            fuehreAus()
            spiel.stoppeSpiel()
        }
    }

    /**
     * Startet das [EinfachesHamsterSpiel].
     *
     * Nachdem das Spiel geladen und gestartet wurde, können Befehle ausgeführt werden.
     */
    fun starteSpiel() {
        runBlocking {
            spiel.starteSpiel(startePausiert = false)
        }
    }

    /**
     * Startet das [EinfachesHamsterSpiel] und [pausiert][de.github.dudrie.hamster.core.game.SpielModus.Pausiert] es direkt.
     *
     * Bevor Befehle ausgeführt werden können, muss das Spiel mit [setzeSpielFort] fortgesetzt werden.
     */
    fun starteSpielPausiert() {
        runBlocking {
            spiel.starteSpiel(startePausiert = true)
        }
    }

    /**
     * [Stoppt][de.github.dudrie.hamster.core.game.SpielModus.Gestoppt] das aktuelle Spiel.
     *
     * Anschließend können **keine** weiteren Befehle mehr ausgeführt werden.
     */
    fun stoppeSpiel() {
        runBlocking {
            spiel.stoppeSpiel()
        }
    }

    /**
     * [Pausiert][de.github.dudrie.hamster.core.game.SpielModus.Pausiert] das Spiel.
     *
     * Es kann mit [setzeSpielFort] fortgesetzt werden.
     */
    fun pausiereSpiel() {
        runBlocking {
            spiel.pausiereSpiel()
        }
    }

    /**
     * Setzt ein [pausiertes][de.github.dudrie.hamster.core.game.SpielModus.Pausiert] Spiel fort, sodass anschließend wieder Befehle ausgeführt werden können.
     */
    fun setzeSpielFort() {
        spiel.setzeSpielFort()
    }

    /**
     * Setzt die Geschwindigkeit des Spiels auf die übergebene [Geschwindigkeit][geschwindigkeit].
     *
     * @see SpielViewModel.setGeschwindigkeit
     */
    fun setGeschwindigkeit(geschwindigkeit: Double) {
        spiel.setGeschwindigkeit(geschwindigkeit)
    }

}
