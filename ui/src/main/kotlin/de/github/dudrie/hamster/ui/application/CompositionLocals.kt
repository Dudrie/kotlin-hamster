package de.github.dudrie.hamster.ui.application

import androidx.compose.runtime.compositionLocalOf
import de.github.dudrie.hamster.interfaces.AbstractEditableTerritory
import de.github.dudrie.hamster.interfaces.AbstractHamsterGame
import de.github.dudrie.hamster.ui.theme.GameColors

/**
 * Provides the [AbstractHamsterGame] which serves the data for the app's game.
 */
internal val HamsterGameLocal = compositionLocalOf<AbstractHamsterGame> { error("No hamster game was provided.") }

/**
 * Provides the [GameColors] object.
 */
internal val GameColorsLocal = compositionLocalOf<GameColors> { error("No game theme was provided.") }

/**
 * Provides the [AbstractEditableTerritory] object for the editor.
 */
internal val EditorTerritoryLocal =
    compositionLocalOf<AbstractEditableTerritory> { error("No editable territory was provided.") }
