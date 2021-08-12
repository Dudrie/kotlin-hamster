package de.github.dudrie.hamster.internal.model.game

import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.territory.GameTerritory

data class HamsterGameViewModel(val territory: GameTerritory, val hamster: GameHamster)
