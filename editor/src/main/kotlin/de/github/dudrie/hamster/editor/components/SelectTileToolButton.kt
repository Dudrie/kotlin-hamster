package de.github.dudrie.hamster.editor.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.editor.application.EditorState
import de.github.dudrie.hamster.editor.tools.TileTool

/**
 * Button like composable which is used to select a tool.
 *
 * @param tool Tool to select. Can be `null`. If it is `null` the button will deselect the currently selected tool.
 * @param icon Icon to show on the left of the button.
 * @param text Text to show to the right of the [icon].
 * @param modifier [Modifier] applied to the underlying [Surface].
 */
@Composable
fun SelectTileToolButton(
    tool: TileTool?,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
) {
    var selectedTileTool by EditorState.selectedTool
    val isSelected by produceState(false, selectedTileTool, tool) { value = selectedTileTool == tool }
    val border = if (isSelected) {
        BorderStroke(1.dp, MaterialTheme.colors.primary)
    } else {
        ButtonDefaults.outlinedBorder
    }
    val color = if (isSelected) {
        MaterialTheme.colors.primary.copy(alpha = 0.15f)
    } else {
        MaterialTheme.colors.surface
    }

    EditorToolboxButton(
        onClick = { selectedTileTool = tool },
        icon = icon,
        text = text,
        color = color,
        border = border,
        modifier = modifier
    )
}
