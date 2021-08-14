package de.github.dudrie.hamster.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

internal object GameColors {
    val defaultHamsterColor: Color = Color(68, 114, 196)
    val floorColor: Color = Color(255, 221, 136)
}

internal object GameTheme {
    val colors: GameColors
        @Composable
        get() = GameColorsLocal.current

}

internal val GameColorsLocal = compositionLocalOf<GameColors> { error("No game theme was provided.") }
