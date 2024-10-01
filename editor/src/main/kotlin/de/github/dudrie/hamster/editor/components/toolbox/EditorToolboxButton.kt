package de.github.dudrie.hamster.editor.components.toolbox

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

@Composable
fun EditorToolboxButton(
    onClick: () -> Unit,
    text: String,
    image: Painter,
    highlight: Boolean = false,
    color: Color = MaterialTheme.colorScheme.background,
    border: BorderStroke = ButtonDefaults.outlinedButtonBorder,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val surfaceColor = if (highlight) MaterialTheme.colorScheme.secondaryContainer else color
    val surfaceBorder =
        if (highlight) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else border

    Surface(
        color = surfaceColor,
        contentColor = MaterialTheme.colorScheme.contentColorFor(surfaceColor),
        shape = RoundedCornerShape(8.dp),
        border = surfaceBorder,
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.defaultMinSize(minHeight = 48.dp).padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(image, null, modifier = Modifier.size(36.dp))

            Text(text = text, modifier = Modifier.padding(start = 8.dp))
        }
    }
}
