package de.github.dudrie.hamster.internal.model.hamster.commands

import de.github.dudrie.hamster.execptions.DestinationOutsideTerritoryException
import de.github.dudrie.hamster.execptions.TileBlockedException
import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * [Command] that spawns the given [hamster] inside its territory.
 *
 * @param hamster [GameHamster] to spawn.
 */
class SpawnHamsterCommand(private val hamster: GameHamster) : Command() {

    private var tileToSpawnOn: GameTile = hamster.tile

    /**
     * Spawns the [hamster] on its tile ([tileToSpawnOn]).
     */
    override fun execute() {
        tileToSpawnOn.addContent(hamster)
        tileToSpawnOn = hamster.tile
    }

    /**
     * Removes the [hamster] from the [tileToSpawnOn].
     */
    override fun undo() {
        tileToSpawnOn.removeContent(hamster)
    }

    /**
     * Returns a [List] containing exceptions as follows:
     * - [DestinationOutsideTerritoryException]: The [tileToSpawnOn] is outside the [GameTerritory][de.github.dudrie.hamster.internal.model.territory.GameTerritory]
     * - [TileBlockedException]: The tile is blocked (ie by a wall) and therefore the [hamster] cannot be placed upon it.
     */
    override fun getExceptionsFromCommandExecution(): List<RuntimeException> {
        val list = mutableListOf<RuntimeException>()

        if (!hamster.territory.isLocationInside(tileToSpawnOn.location)) {
            list += DestinationOutsideTerritoryException(tileToSpawnOn.location)
        }

        if (tileToSpawnOn.blocked) {
            list += TileBlockedException(tileToSpawnOn)
        }

        return list
    }

    override fun getCommandLogMessage(): String =
        HamsterString.getWithFormat("command.spawn.hamster", hamster.tile.location)

}
