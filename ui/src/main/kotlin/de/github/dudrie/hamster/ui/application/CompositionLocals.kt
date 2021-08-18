package de.github.dudrie.hamster.ui.application

import androidx.compose.runtime.compositionLocalOf
import de.github.dudrie.hamster.interfaces.AbstractEditableTerritory
import de.github.dudrie.hamster.interfaces.AbstractHamsterGame

/**
 * Provides the [AbstractHamsterGame] which serves the data for the app's game.
 */
internal val HamsterGameLocal = compositionLocalOf<AbstractHamsterGame> { error("No hamster game was provided.") }

/**
 * Provides the [AbstractEditableTerritory] object for the editor.
 */
internal val EditorTerritoryLocal =
    compositionLocalOf<AbstractEditableTerritory> { error("No editable territory was provided.") }
