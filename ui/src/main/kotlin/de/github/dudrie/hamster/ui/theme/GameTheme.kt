package de.github.dudrie.hamster.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import de.github.dudrie.hamster.ui.application.GameColorsLocal

/**
 * Object holding colors important to the game.
 */
internal object GameColors {
    /**
     * Default color used in the hamster's image.
     *
     * Can be used to change the main color of the image if one has multiple hamsters.
     */
    val defaultHamsterColor: Color = Color(68, 114, 196)

    /**
     * Color of a [floor][de.github.dudrie.hamster.internal.model.territory.GameTileType.Floor] tile.
     */
    val floorColor: Color = Color(255, 221, 136)
}

/**
 * Object holding properties important for theming the game.
 */
internal object GameTheme {
    /**
     * Colors related to the game.
     *
     * @see GameColors
     */
    val colors: GameColors
        @Composable
        get() = GameColorsLocal.current

}
