package de.github.dudrie.hamster.ui.application

import androidx.compose.runtime.compositionLocalOf
import de.github.dudrie.hamster.interfaces.AbstractHamsterGame
import de.github.dudrie.hamster.ui.application.state.UIState

/**
 * Provides the [AbstractHamsterGame] which serves the data for the app's game.
 */
internal val HamsterGameLocal = compositionLocalOf<AbstractHamsterGame> { error("No hamster game was provided.") }

/**
 * Provides the [UIState] of the app.
 */
val UIStateLocal = compositionLocalOf<UIState> { error("No UIState local was provided.") }
