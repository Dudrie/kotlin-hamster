package de.github.dudrie.hamster.editor.sidepanel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.editor.application.EditorState
import de.github.dudrie.hamster.editor.components.EditorToolboxButton
import de.github.dudrie.hamster.editor.components.SelectTileToolButton
import de.github.dudrie.hamster.editor.tools.MakeFloorTool
import de.github.dudrie.hamster.editor.tools.MakeWallTool
import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.internal.model.territory.GameTileType
import de.github.dudrie.hamster.ui.R
import de.github.dudrie.hamster.ui.components.ResourceIcon
import de.github.dudrie.hamster.ui.components.ResourceIconSize
import de.github.dudrie.hamster.ui.helpers.getResource

/**
 * Toolbox composable which contains different tools to use in the editor.
 */
@Composable
fun EditorToolbox(modifier: Modifier = Modifier) {
    Column(modifier.padding(8.dp)) {
        val iconSize = ResourceIconSize.Medium

        Text(
            HamsterString.get("editor.toolbox.title"),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SelectTileToolButton(
            tool = remember { null },
            icon = { ResourceIcon(R.icons.selectTool, modifier = Modifier.size(iconSize.value)) },
            text = { Text(HamsterString.get("editor.toolbox.tool.selection")) }
        )

        SelectTileToolButton(
            tool = remember { MakeWallTool() },
            icon = { ResourceIcon(GameTileType.Wall.getResource(), size = iconSize, tint = Color.Unspecified) },
            text = { Text(HamsterString.get("editor.toolbox.tool.wall")) }
        )

        SelectTileToolButton(
            tool = remember { MakeFloorTool() },
            icon = { ResourceIcon(GameTileType.Floor.getResource(), size = iconSize, tint = Color.Unspecified) },
            text = { Text(HamsterString.get("editor.toolbox.tool.floor")) }
        )

        Divider(Modifier.padding(vertical = 16.dp))

        Text(
            HamsterString.get("editor.toolbox.actions.title"),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        EditorToolboxButton(
            onClick = { EditorState.surroundTerritoryWithWalls() },
            icon = { ResourceIcon(R.icons.surroundWithWalls, size = iconSize, tint = Color.Unspecified) },
            text = { Text(HamsterString.get("editor.toolbox.surround.with.walls")) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

