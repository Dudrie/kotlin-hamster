package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.importer.helpers.ResourceReader
import de.github.dudrie.hamster.internal.model.hamster.HamsterTileContent
import de.github.dudrie.hamster.ui.R
import de.github.dudrie.hamster.ui.application.UIStateLocal

/**
 * Responsible for displaying a [HamsterTileContent] on a [tile][BoardTile].
 *
 * @param content The [HamsterTileContent] to show.
 */
@Composable
fun BoardHamsterTileContent(content: HamsterTileContent) {
    val hideHamster = UIStateLocal.current.hideHamster

    val degrees = getDegreesForDirection(content.direction)
    val hamster = remember { ResourceReader(R.images.hamster).getContentAsImage().toComposeImageBitmap() }

    Image(
        bitmap = hamster,
        contentDescription = null,
        modifier = Modifier.fillMaxSize().padding(4.dp).rotate(degrees),
        alpha = if (hideHamster) 0.35f else DefaultAlpha
    )
}
