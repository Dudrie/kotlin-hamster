package de.github.dudrie.hamster.ui.application

import androidx.compose.runtime.compositionLocalOf
import de.github.dudrie.hamster.interfaces.IHamsterGame

/**
 * Provides the [IHamsterGame] which serves the data for the app's game.
 */
internal val HamsterGameLocal = compositionLocalOf<IHamsterGame> { error("No hamster game was provided.") }
