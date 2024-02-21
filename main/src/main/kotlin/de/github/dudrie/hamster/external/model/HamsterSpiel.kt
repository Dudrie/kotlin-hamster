package de.github.dudrie.hamster.external.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.github.dudrie.hamster.importer.InitialGameImporter
import de.github.dudrie.hamster.interfaces.AbstraktesHamsterSpiel
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.game.GameCommandStack
import de.github.dudrie.hamster.internal.model.game.GameMode
import de.github.dudrie.hamster.ui.game.HamsterGameCompose

/**
 * Grundklasse, um ein Spiel zu laden und zu starten.
 *
 * @param territoriumsDatei Pfad zur Territoriumsdatei, die geladen werden soll. Es wird ein Standardterritorium geladen, wenn weggelassen.
 */
class HamsterSpiel(territoriumsDatei: String? = null) : AbstraktesHamsterSpiel() {

    /**
     * [GameCommandStack], welcher zu diesem [HamsterSpiel] gehört.
     */
    override val gameCommands: GameCommandStack = GameCommandStack()

    /**
     * [Territorium] dieses Spiels.
     *
     * Ist erst initialisiert, wenn [isInitialized] `true` ist.
     */
    override lateinit var territorium: Territorium
        private set

    /**
     * Das Spiel ist initialisiert, nachdem das dazugehörige Territorium vollständig geladen wurde.
     */
    override var isInitialized by mutableStateOf(false)
        private set

    /**
     * Standard [Hamster], der im Spiel benutzt wird. Er heißt hier "Paule".
     *
     * Ist erst initialisiert, wenn [isInitialized] `true` ist.
     */
    var paule: Hamster
        private set

    private val composeGameHandler: HamsterGameCompose = HamsterGameCompose(this)

    init {
        val importer = InitialGameImporter(hamsterGame = this@HamsterSpiel, territoryFile = territoriumsDatei)

        territorium = importer.territory
        paule = importer.hamster
        isInitialized = true
    }

    /**
     * Startet das [HamsterSpiel].
     *
     * Nachdem das Spiel geladen und gestartet wurde, können Befehle ausgeführt werden.
     *
     * @param startePausiert Wenn `true`, wird das Spiel vollständig initialisiert, aber anschließend in den [Pausiert-Modus][GameMode.Paused] anstatt in den [Ausführen-Modus][GameMode.Running] versetzt.
     */
    override fun starteSpiel(startePausiert: Boolean) {
        composeGameHandler.startGame()
        gameCommands.startGame(startePausiert)
    }

    /**
     * Executes the given [command] to change the state of this game.
     */
    override fun executeCommand(command: Command) {
        gameCommands.executeCommand(command)
    }
}
