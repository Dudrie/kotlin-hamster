package de.github.dudrie.hamster.internal.model.hamster.commands

import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.hamster.GameHamster

/**
 * [Command] that turns the [hamster] 90 degrees counterclockwise.
 *
 * @param hamster [GameHamster] this [Command] should be executed with.
 */
class TurnLeftCommand(private val hamster: GameHamster) : Command() {
    /**
     * Set to the old facing [Direction] if this [Command] was successfully executed.
     */
    private var oldDirection: Direction? = null

    /**
     * Turns the [hamster] 90 degrees counterclockwise.
     */
    override fun execute() {
        oldDirection = hamster.direction
        hamster.turnLeft()
    }

    /**
     * Turns the [hamster] 90 degrees clockwise.
     *
     * The [Command] must have been successfully executed beforehand.
     */
    override fun undo() {
        oldDirection?.let {
            hamster.turnTo(it)
            oldDirection = null
        }
    }

    /**
     * Always returns an empty [List].
     *
     * There are no conditions to turn the [hamster].
     *
     * @return An empty [List].
     */
    override fun getExceptionsFromCommandExecution(): List<RuntimeException> {
        return listOf()
    }

    override fun getCommandLogMessage(): String = HamsterString.get("command.turn.left")
}
