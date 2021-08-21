package de.github.dudrie.hamster.internal.model.hamster.commands

import de.github.dudrie.hamster.execptions.MouthEmptyException
import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * [Command] which lets the [hamster] drop a grain on its tile.
 *
 * @param hamster [GameHamster] with which this [Command] should be executed.
 */
class DropGrainCommand(private val hamster: GameHamster) : Command() {

    /**
     * If this command was successfully executed this is the [GameTile] the [hamster] dropped the grain onto.
     */
    private var tileGrainWasPutOn: GameTile? = null


    /**
     * The hamster drops a grain onto the tile it stands on.
     *
     * The [hamster] needs at least one grain in its mouth to be able to drop a grain.
     *
     * This function updates the grain count of the [hamster] and the [GameTile] it stands on accordingly.
     */
    override fun execute() {
        require(canBeExecuted()) { "Drop grain command cannot be executed." }
        val tile = hamster.currentTile
        hamster.dropGrain()
        tile.addGrainToTile()
        tileGrainWasPutOn = tile
    }

    /**
     * The dropped grain gets removed from the [GameTile] and be given back to the [hamster].
     *
     * The [Command] must have been successfully executed beforehand.
     */
    override fun undo() {
        tileGrainWasPutOn?.let {
            hamster.pickGrain()
            it.removeGrainFromTile()
            tileGrainWasPutOn = null
        }
    }

    /**
     * Returns a [List] with a single [MouthEmptyException] if the [hamster] has no grain in its mouth.
     *
     * If the [hamster] has grains in its mouth the returned [List] is empty.
     *
     * @return A list as described above.
     */
    override fun getExceptionsFromCommandExecution(): List<RuntimeException> {
        if (hamster.grainCount <= 0) {
            return listOf(MouthEmptyException())
        }
        return listOf()
    }

    override fun getCommandLogMessage(): String {
        val tile = tileGrainWasPutOn
        return if (tile != null) {
            HamsterString.getWithFormat("command.drop.grain.log.on.location", tile.location)
        } else {
            HamsterString.get("command.drop.grain.log.no.location.given")
        }
    }
}
