package de.github.dudrie.hamster.external.model

import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.hamster.commands.DropGrainCommand
import de.github.dudrie.hamster.internal.model.hamster.commands.MoveCommand
import de.github.dudrie.hamster.internal.model.hamster.commands.PickGrainCommand
import de.github.dudrie.hamster.internal.model.hamster.commands.TurnLeftCommand

class Hamster(territory: Territory, location: Location, direction: Direction, grainCount: Int) {

    private val internalHamster: GameHamster

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

    fun move() {
        game.executeCommand(MoveCommand(internalHamster))
    }

    fun turnLeft() {
        game.executeCommand(TurnLeftCommand(internalHamster))
    }

    fun pickGrain() {
        game.executeCommand(PickGrainCommand(internalHamster))
    }

    fun dropGrain() {
        game.executeCommand(DropGrainCommand(internalHamster))
    }

    // TODO: Add other helper functions!
}
