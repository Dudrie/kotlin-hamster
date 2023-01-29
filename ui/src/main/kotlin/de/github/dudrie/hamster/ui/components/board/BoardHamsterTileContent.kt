package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.importer.helpers.ResourceReader
import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.hamster.HamsterTileContent
import de.github.dudrie.hamster.ui.R
import de.github.dudrie.hamster.ui.application.UIStateLocal
import de.github.dudrie.hamster.ui.theme.GameTheme

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

    Box(
        modifier = Modifier.fillMaxSize().alpha(if (hideHamster) 0.35f else DefaultAlpha),
        contentAlignment = Alignment.Center
    ) {
        Image(
            bitmap = hamster,
            contentDescription = null,
            modifier = Modifier.fillMaxSize().padding(4.dp).rotate(degrees)
        )

        if (content is GameHamster && content.territory.hasMultipleHamsters()) {
            Surface(
                color = Color.Gray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(25),
                modifier = Modifier.padding(2.dp).size(24.dp).align(Alignment.BottomStart)
            ) {
                Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                    Text(
                        text = content.hamsterNumber.toString(),
                        style = GameTheme.typography.grainCount,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


