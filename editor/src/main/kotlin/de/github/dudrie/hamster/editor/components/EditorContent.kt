package de.github.dudrie.hamster.editor.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ui.generated.Res
import de.github.dudrie.hamster.ui.generated.floor
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun EditorContent(modifier: Modifier = Modifier) {
    Row(modifier) {
        Box(Modifier.weight(1f)) {
            Text("Hallo alle")
        }

        Column(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 12.dp).width(300.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SelectTileToolButton(
                tool = TileTool(),
                text = "Wand auswählen",
                icon = painterResource(Res.drawable.floor)
            )

            SelectTileToolButton(
                tool = TileTool(),
                text = "Wand auswählen",
                icon = painterResource(Res.drawable.floor)
            )

            SelectTileToolButton(
                tool = TileTool(),
                text = "Wand auswählen",
                icon = painterResource(Res.drawable.floor)
            )

            SelectTileToolButton(
                tool = TileTool(),
                text = "Wand auswählen",
                icon = painterResource(Res.drawable.floor)
            )
        }
    }
}

@Composable
fun SelectTileToolButton(
    tool: TileTool,
    text: String,
    icon: Painter,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    EditorToolboxButton(onClick = {}, text = text, icon = icon, modifier = modifier)
}

@Composable
fun EditorToolboxButton(
    onClick: () -> Unit,
    text: String,
    icon: Painter,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    border: BorderStroke = ButtonDefaults.outlinedButtonBorder,
    modifier: Modifier = Modifier
) {
    Surface(
        color = color,
        contentColor = MaterialTheme.colorScheme.contentColorFor(color),
        shape = RoundedCornerShape(8.dp),
        border = border,
        onClick = onClick,
        shadowElevation = 4.dp,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.defaultMinSize(minHeight = 48.dp).padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(imageResource(Res.drawable.floor), null, modifier = Modifier.size(36.dp))

            Text(text = text, modifier = Modifier.padding(start = 8.dp))
        }
    }
}

class TileTool {}
