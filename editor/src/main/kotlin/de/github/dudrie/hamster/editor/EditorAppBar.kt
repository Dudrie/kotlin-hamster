package de.github.dudrie.hamster.editor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ResString
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.editor.dialog.DialogState
import de.github.dudrie.hamster.ui.components.ControlButtonColors

/**
 * [TopAppBar] for the editor.
 */
@Composable
fun EditorAppBar() {
    TopAppBar(elevation = 8.dp, contentPadding = PaddingValues(horizontal = 16.dp)) {
        val padding = 16.dp

        // TODO: Make abstract AppBarButton which is used here and in ControlButton (:ui).
        OutlinedButton(
            {
                DialogState.showConfirmDialog(
                    text = { Text(ResString.get("editor.dialog.new.territory.text")) },
                    title = { Text(ResString.get("editor.dialog.new.territory.title")) },
                    confirm = { Text(ResString.get("editor.dialog.new.territory.confirm")) },
                    onConfirm = { EditorState.resetTerritory() },
                    dismiss = { Text(ResString.get("button.cancel")) },
                    onDismiss = {}
                )
            },
            colors = ControlButtonColors,
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
            modifier = Modifier.padding(start = padding, end = padding)
        ) { Text("NEW") }

        OutlinedButton(
            { TODO("Not implemented") },
            colors = ControlButtonColors,
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
            modifier = Modifier.padding(end = padding)
        ) { Text("SAVE") }

        OutlinedButton(
            { TODO("Not implemented") },
            colors = ControlButtonColors,
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
            modifier = Modifier.padding(end = padding)
        ) { Text("LOAD") }

        OutlinedButton(
            {
                // TODO: Add dialog to set the size!
                EditorState.setTerritorySize(Size(3, 2))
            },
            colors = ControlButtonColors,
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
            modifier = Modifier.padding(end = padding)
        ) { Text("CHANGE SIZE") }
    }
}
