package de.github.dudrie.hamster.ui.application

import androidx.compose.runtime.compositionLocalOf
import de.github.dudrie.hamster.interfaces.IHamsterGame
import de.github.dudrie.hamster.ui.theme.GameColors

/**
 * Provides the [IHamsterGame] which serves the data for the app's game.
 */
internal val HamsterGameLocal = compositionLocalOf<IHamsterGame> { error("No hamster game was provided.") }

/**
 * Provides the [GameColors] object.
 */
internal val GameColorsLocal = compositionLocalOf<GameColors> { error("No game theme was provided.") }
