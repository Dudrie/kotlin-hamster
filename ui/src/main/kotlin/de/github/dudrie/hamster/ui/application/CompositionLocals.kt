package de.github.dudrie.hamster.ui.application

import androidx.compose.runtime.compositionLocalOf
import de.github.dudrie.hamster.interfaces.AbstraktesHamsterSpiel
import de.github.dudrie.hamster.ui.application.state.UIState

/**
 * Provides the [AbstraktesHamsterSpiel] which serves the data for the app's game.
 */
internal val LocalHamsterGame = compositionLocalOf<AbstraktesHamsterSpiel> { error("No hamster game was provided.") }

/**
 * Provides the [UIState] of the app.
 */
val LocalUIState = compositionLocalOf<UIState> { error("No UIState local was provided.") }
