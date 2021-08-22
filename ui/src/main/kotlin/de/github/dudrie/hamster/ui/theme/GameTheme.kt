package de.github.dudrie.hamster.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Object holding information about the hamster's colors.
 *
 * @param lightPart The light part of the hamster.
 * @param darkPart The dark part of the hamster.
 */
data class HamsterColors(val lightPart: Color, val darkPart: Color)

/**
 * Object holding colors important to the game.
 */
object GameColors {
    /**
     * Color of the hamster image used by game.
     */
    val hamsterImage = HamsterColors(lightPart = Color(217, 163, 0), darkPart = Color(184, 138, 0))

    /**
     * Color of the default hamster.
     */
    val defaultHamster = HamsterColors(lightPart = Color(155, 213, 255), darkPart = Color(99, 164, 255))

    /**
     * Color of a [floor][de.github.dudrie.hamster.internal.model.territory.GameTileType.Floor] tile.
     */
    val floor: Color = Color(255, 221, 136)
}

/**
 * Object holding properties important for theming the game.
 */
object GameTheme {
    /**
     * Colors related to the game.
     *
     * @see GameColors
     */
    val colors: GameColors
        @Composable
        get() = GameColorsLocal.current
}

/**
 * Provides the current [GameColors] object.
 */
internal val GameColorsLocal = compositionLocalOf<GameColors> { error("No game colors were provided.") }
