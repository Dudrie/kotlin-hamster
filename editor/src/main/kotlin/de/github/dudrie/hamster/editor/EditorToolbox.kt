package de.github.dudrie.hamster.editor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.editor.tools.MakeFloorTool
import de.github.dudrie.hamster.editor.tools.MakeWallTool
import de.github.dudrie.hamster.editor.tools.TileTool
import de.github.dudrie.hamster.ui.R
import de.github.dudrie.hamster.ui.components.ResourceIcon
import de.github.dudrie.hamster.ui.components.ResourceIconSize
import de.github.dudrie.hamster.ui.theme.GameTheme

/**
 * Toolbox composable which contains different tools to use in the editor.
 */
@Composable
fun EditorToolbox(modifier: Modifier = Modifier) {
    Column(modifier.padding(8.dp)) {
        val iconSize = ResourceIconSize.Medium

        Text("TOOLS", style = MaterialTheme.typography.h5, modifier = Modifier.padding(bottom = 16.dp))

        SelectTileToolButton(
            tool = remember { null },
            icon = { ResourceIcon(R.icons.selectTool, modifier = Modifier.size(iconSize.value)) },
            text = { Text("SELECTION TOOL") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        SelectTileToolButton(
            tool = remember { MakeWallTool() },
            icon = { ResourceIcon(R.images.wall, size = iconSize, tint = Color.Unspecified) },
            text = { Text("WALL TOOL") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        SelectTileToolButton(
            tool = remember { MakeFloorTool() },
            icon = {
                Box(
                    Modifier.background(GameTheme.colors.floor).border(1.dp, MaterialTheme.colors.onSurface)
                        .size(iconSize.value)
                )
            },
            text = { Text("FLOOR TOOL") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
    }
}

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

