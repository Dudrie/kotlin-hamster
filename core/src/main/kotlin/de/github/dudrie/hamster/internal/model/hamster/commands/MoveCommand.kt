package de.github.dudrie.hamster.internal.model.hamster.commands

import de.github.dudrie.hamster.execptions.DestinationOutsideTerritoryException
import de.github.dudrie.hamster.execptions.PathBlockedException
import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * [Command] that moves the [hamster] one tile in its facing [direction][GameHamster.direction].
 *
 * @param hamster [GameHamster] with which this [Command] should be executed.
 */
class MoveCommand(private val hamster: GameHamster) : Command() {
    /**
     * Is set to the [GameTile] the [hamster] was before executing this [Command] if the command was successfully executed.
     */
    private var oldTile: GameTile? = null

    /**
     * Moves the [hamster] one tile in its facing direction.
     *
     * The destination tile must not be blocked for movement.
     *
     * The old and the new tiles are updated accordingly.
     */
    override fun execute() {
        require(canBeExecuted()) { "Move command cannot be executed." }
        oldTile = hamster.currentTile
        hamster.move()
    }

    /**
     * Moves the [hamster] back to the tile where it was before the command was executed.
     *
     * The [Command] must have been successfully executed beforehand.
     */
    override fun undo() {
        oldTile?.let {
            hamster.moveTo(it)
            oldTile = null
        }
    }

    /**
     * Returns a [List] which contains an exception as follows:
     *
     * - [DestinationOutsideTerritoryException]: The new [GameTile] is outside the [GameTerritory][de.github.dudrie.hamster.internal.model.territory.GameTerritory] therefore movement is not possible.
     * - [PathBlockedException]: The new [GameTile] is blocked for movement.
     *
     * If none of the above applies the returned [List] is empty.
     */
    override fun getExceptionsFromCommandExecution(): List<RuntimeException> {
        val list = mutableListOf<RuntimeException>()

        try {
            val newLocation = hamster.getLocationAfterMove()

            if (!hamster.territory.isLocationInside(newLocation)) {
                list += DestinationOutsideTerritoryException(newLocation)
            } else {
                val newTile = hamster.territory.getTileAt(newLocation)
                if (newTile.blocked) {
                    list += PathBlockedException(newTile)
                }
            }
        } catch (e: RuntimeException) {
            list += e
        }

        return list
    }

    override fun getCommandLogMessage(): String = HamsterString.get("command.move.log")
}
