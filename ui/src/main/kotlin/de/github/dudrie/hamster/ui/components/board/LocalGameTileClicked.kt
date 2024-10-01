package de.github.dudrie.hamster.ui.components.board

import androidx.compose.runtime.compositionLocalOf
import de.github.dudrie.hamster.core.model.util.Position

typealias GameTileClickListener = (Position) -> Unit

val LocalGameTileClicked = compositionLocalOf<GameTileClickListener?> { null }
