package de.github.dudrie.hamster.internal.model.hamster.commands

import de.github.dudrie.hamster.execptions.NoGrainsOnTileException
import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * [Command] which lets the [hamster] pick up a grain from its tile.
 *
 * @param hamster [GameHamster] with which this [Command] should be executed.
 */
class PickGrainCommand(private val hamster: GameHamster) : Command() {

    /**
     * If this command was successfully executed this is the [GameTile] the [hamster] picked the grain from.
     */
    private var tileGrainWasPickedFrom: GameTile? = null

    /**
     * The hamster picks up a grain from the tile it stands on.
     *
     * The [GameTile] needs at least one grain on it for the [hamster] to pick up.
     *
     * This function updates the grain count of the [hamster] and the [GameTile] it stands on accordingly.
     */
    override fun execute() {
        require(canBeExecuted()) { "Pick grain command cannot be executed." }
        val tile = hamster.tile
        tile.removeGrainFromTile()
        hamster.pickGrain()
        tileGrainWasPickedFrom = tile
    }

    /**
     * The picked up grain gets added back to the [GameTile] and be removed from to the [hamster].
     *
     * The [Command] must have been successfully executed beforehand.
     */
    override fun undo() {
        tileGrainWasPickedFrom?.let {
            hamster.dropGrain()
            it.addGrainToTile()
            tileGrainWasPickedFrom = null
        }
    }

    /**
     * Returns a [List] with a single [NoGrainsOnTileException] if the [GameTile] does not have a grain on it.
     *
     * If the [GameTile] has grains on it the returned [List] is empty.
     *
     * @return A list as described above.
     */
    override fun getExceptionsFromCommandExecution(): List<RuntimeException> {
        if (hamster.tile.grainCount <= 0) {
            return listOf(NoGrainsOnTileException(hamster.tile))
        }
        return listOf()
    }

    override fun getCommandLogMessage(): String {
        val tile = tileGrainWasPickedFrom
        return if (tile != null) {
            HamsterString.getWithFormat("command.pick.grain.log.on.location", tile.location)
        } else {
            HamsterString.get("command.pick.grain.log.no.location.given")
        }
    }
}
