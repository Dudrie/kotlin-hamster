package de.github.dudrie.hamster.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

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
     * Color of the grains on the board.
     */
    val grainColor = Color(109, 76, 65)
}

/**
 * Object holding typographies used in the game.
 */
object GameTypography {
    /**
     * Text style used to display grain counts on the board.
     */
    val grainCount = TextStyle.Default.copy(
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
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

    val typography: GameTypography
        @Composable
        get() = GameTypographyLocal.current
}

/**
 * Provides the current [GameColors] object.
 */
internal val GameColorsLocal = compositionLocalOf<GameColors> { error("No game colors were provided.") }

/**
 * Provides the current [GameTypography] object.
 */
internal val GameTypographyLocal = compositionLocalOf<GameTypography> { error("No game typography were provided.") }
