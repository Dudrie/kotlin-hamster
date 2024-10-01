package de.github.dudrie.hamster.editor.components.toolbox

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.editor.generated.*
import de.github.dudrie.hamster.editor.generated.Res
import de.github.dudrie.hamster.editor.generated.tool_select_name
import de.github.dudrie.hamster.editor.generated.tool_wall_name
import de.github.dudrie.hamster.editor.generated.tools_title
import de.github.dudrie.hamster.editor.model.EditorUIState
import de.github.dudrie.hamster.editor.tools.*
import de.github.dudrie.hamster.ui.generated.floor
import de.github.dudrie.hamster.ui.generated.wall
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import de.github.dudrie.hamster.ui.generated.Res as UIRes

@Composable
fun ToolBox(modifier: Modifier = Modifier, state: EditorUIState = viewModel()) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(Res.string.tools_title),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        EditorToolboxButton(
            onClick = { state.selectedTool = SelectTileTool },
            highlight = state.selectedTool == SelectTileTool,
            text = stringResource(Res.string.tool_select_name),
            image = painterResource(Res.drawable.outline_highlight_alt_black_36dp)
        )

        EditorToolboxButton(
            onClick = { state.selectedTool = MakeWallTool },
            highlight = state.selectedTool == MakeWallTool,
            text = stringResource(Res.string.tool_wall_name),
            image = painterResource(UIRes.drawable.wall)
        )

        EditorToolboxButton(
            onClick = { state.selectedTool = MakeFloorTool },
            highlight = state.selectedTool == MakeFloorTool,
            text = stringResource(Res.string.tool_floor_name),
            image = painterResource(UIRes.drawable.floor)
        )

        EditorToolboxButton(
            onClick = { state.selectedTool = AddGrainToTileTool },
            highlight = state.selectedTool == AddGrainToTileTool,
            text = stringResource(Res.string.tool_add_grain_name),
            image = painterResource(Res.drawable.grain_plus_one)
        )

        EditorToolboxButton(
            onClick = { state.selectedTool = RemoveGrainFromTileTool },
            highlight = state.selectedTool == RemoveGrainFromTileTool,
            text = stringResource(Res.string.tool_remove_grain_name),
            image = painterResource(Res.drawable.grain_minus_one)
        )

        HorizontalDivider()

        Text(
            text = stringResource(Res.string.actions_title),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        EditorToolboxButton(
            onClick = {},
            text = stringResource(Res.string.actions_surround_walls_name),
            image = painterResource(Res.drawable.dots_square)
        )
    }
}
