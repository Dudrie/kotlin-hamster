package de.github.dudrie.hamster.editor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.editor.tools.TileTool

/**
 * Button like composable which is used to select a tool.
 *
 * @param tool Tool to select. Can be `null`. If it is `null` the button will deselect the currently selected tool.
 * @param icon Icon to show on the left of the button.
 * @param text Text to show to the right of the [icon].
 * @param modifier [Modifier] applied to the underlying [Surface].
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectTileToolButton(
    tool: TileTool?,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier
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

    Surface(
        onClick = { selectedTileTool = tool },
        role = Role.Button,
        color = color,
        contentColor = MaterialTheme.colors.contentColorFor(color),
        shape = MaterialTheme.shapes.small,
        border = border,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.defaultMinSize(minWidth = ButtonDefaults.MinWidth, minHeight = 48.dp).padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon()

            Box(Modifier.padding(start = 8.dp)) {
                text()
            }
        }
    }
}
