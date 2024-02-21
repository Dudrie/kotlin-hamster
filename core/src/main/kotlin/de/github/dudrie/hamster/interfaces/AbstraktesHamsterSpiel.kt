package de.github.dudrie.hamster.interfaces

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import de.github.dudrie.hamster.execptions.TileRelatedException
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.game.GameCommandStack
import de.github.dudrie.hamster.internal.model.game.GameMode
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * Interface für das tatsächliche Hamsterspiel.
 */
abstract class AbstraktesHamsterSpiel {
    /**
     * [GameCommandStack], der zu diesem [AbstraktesHamsterSpiel] gehört.
     */
    abstract val gameCommands: GameCommandStack

    /**
     * [AbstraktesTerritorium] dieses Spiels.
     */
    abstract val territorium: AbstraktesTerritorium

    /**
     * State, der überwacht, ob das Spiel initialisiert wurde.
     */
    abstract val isInitialized: Boolean

    /**
     * State that holds the tile which should be highlighted.
     *
     * If there is no such tile the state holds `null`.
     */
    private val tileToHighlightState: State<GameTile?>
        @Composable
        get() {
            return produceState<GameTile?>(null, gameCommands.gameException) {
                val exception = gameCommands.gameException?.exception
                value = if (exception is TileRelatedException) {
                    exception.tile
                } else {
                    null
                }
            }
        }

    /**
     * The [GameTile] which should get highlighted.
     */
    private val tileToHighlight: GameTile?
        @Composable
        get() = tileToHighlightState.value

    /**
     * Startet das [AbstraktesHamsterSpiel].
     *
     * Nachdem das Spiel geladen und gestartet wurde, können Befehle ausgeführt werden.
     *
     * @param startePausiert Wenn `true`, wird das Spiel vollständig initialisiert, aber anschließend in den [Pausiert-Modus][GameMode.Paused] anstatt in den [Ausführen-Modus][GameMode.Running] versetzt.
     */
    abstract fun starteSpiel(startePausiert: Boolean = false)

    /**
     * Stoppt das aktuelle Spiel.
     *
     * Anschließend können keine weiteren Befehle ausgeführt werden.
     */
    fun stoppeSpiel() = gameCommands.stopGame()

    /**
     * Pausiert das aktuelle Spiel.
     *
     * Es kann mit [setzeSpielFort] fortgesetzt werden.
     *
     * @see setzeSpielFort
     */
    fun pausiereSpiel() = gameCommands.pauseGame()

    /**
     * Setzt ein vorher mit [pausiereSpiel] pausiertes Spiel fort.
     *
     * @see pausiereSpiel
     */
    fun setzeSpielFort() = gameCommands.resumeGame()

    /**
     * Ändert die Spielgeschwindigkeit auf die gegebene [geschwindigkeit].
     *
     * Muss zwischen der [minimalen][de.github.dudrie.hamster.internal.model.game.GameCommandStack.minSpeed] und [maximalen][de.github.dudrie.hamster.internal.model.game.GameCommandStack.maxSpeed] Geschwindigkeit liegen.
     */
    fun setzeSpielGeschwindigkeit(geschwindigkeit: Float) = gameCommands.setGameSpeed(geschwindigkeit)

    /**
     * Executes the given [command] to change the state of this game.
     */
    abstract fun executeCommand(command: Command)

    /**
     * The territory of the game.
     */
    operator fun component1(): AbstraktesTerritorium = territorium

    /**
     * The command stack of the game.
     */
    operator fun component2(): GameCommandStack = gameCommands

    /**
     * The tile which should be highlighted.
     *
     * If none should be highlighted `null` is returned.
     */
    @Composable
    operator fun component3(): GameTile? = tileToHighlight

}
