package de.github.dudrie.hamster.external.model

import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.interfaces.IHamster
import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.hamster.commands.DropGrainCommand
import de.github.dudrie.hamster.internal.model.hamster.commands.MoveCommand
import de.github.dudrie.hamster.internal.model.hamster.commands.PickGrainCommand
import de.github.dudrie.hamster.internal.model.hamster.commands.TurnLeftCommand

/**
 * Hamster used in the game.
 *
 * Creates a corresponding [GameHamster] upon initialization.
 *
 * @param territory [Territory] the hamster should be in.
 * @param location [Location] the hamster is at. Must be inside the [Territory].
 * @param direction Initial [Direction] the hamster faces.
 * @param grainCount Initial amount of grains the hamster has in its mouth.
 */
class Hamster(territory: Territory, location: Location, direction: Direction, grainCount: Int) : IHamster {

    /**
     * Reference to the actual [GameHamster].
     */
    private val internalHamster: GameHamster

    /**
     * [HamsterGame] this hamster is a part of.
     */
    private val game: HamsterGame = territory.hamsterGame

    init {
        val tile = territory.getTileAt(location)
        internalHamster =
            GameHamster(
                territory = territory.internalTerritory,
                tile = tile,
                direction = direction,
                grainCount = grainCount
            )
    }

    /**
     * Moves the hamster one step.
     *
     * The tile in front of the hamster must not be blocked.
     */
    override fun move() {
        game.executeCommand(MoveCommand(internalHamster))
    }

    /**
     * Turns the hamster 90 degrees counterclockwise.
     */
    override fun turnLeft() {
        game.executeCommand(TurnLeftCommand(internalHamster))
    }

    /**
     * Picks up a grain from the tile the hamster currently stands on.
     *
     * The tile must have a grain to pick up.
     */
    override fun pickGrain() {
        game.executeCommand(PickGrainCommand(internalHamster))
    }

    /**
     * Drops a grain unto the tile the hamster currently stand on.
     *
     * The hamster must have a grain to drop in its mouth.
     */
    override fun dropGrain() {
        game.executeCommand(DropGrainCommand(internalHamster))
    }

    // TODO: Add other helper functions!
}
