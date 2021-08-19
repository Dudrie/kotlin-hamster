package de.github.dudrie.hamster.editor

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ResString
import de.github.dudrie.hamster.editor.dialog.ConfirmDialogResult
import de.github.dudrie.hamster.editor.dialog.DialogService
import de.github.dudrie.hamster.ui.components.appbar.AppBarButton
import kotlinx.coroutines.launch

/**
 * [TopAppBar] for the editor.
 */
@Composable
fun EditorAppBar() {
    TopAppBar(elevation = 8.dp, contentPadding = PaddingValues(horizontal = 16.dp)) {
        val padding = 16.dp
        val scope = rememberCoroutineScope()

        // TODO: Make abstract AppBarButton which is used here and in ControlButton (:ui).
        AppBarButton(
            {
                scope.launch {
                    val result = DialogService.askForConfirmation(
                        text = { Text(ResString.get("editor.dialog.new.territory.text")) },
                        title = { Text(ResString.get("editor.dialog.new.territory.title")) },
                        confirm = { Text(ResString.get("editor.dialog.new.territory.confirm")) },
                        dismiss = { Text(ResString.get("button.cancel")) },
                    )
                    if (result == ConfirmDialogResult.Confirm) {
                        EditorState.resetTerritory()
                    }
                }
            },
            modifier = Modifier.padding(start = padding, end = padding),
            true
        ) { Text(ResString.get("editor.appbar.button.new")) }

        AppBarButton(
            { TODO("Not implemented") },
            modifier = Modifier.padding(end = padding),
            true
        ) { Text(ResString.get("editor.appbar.button.save")) }

        AppBarButton(
            { TODO("Not implemented") },
            modifier = Modifier.padding(end = padding),
            true
        ) { Text(ResString.get("editor.appbar.button.open")) }

        AppBarButton(
            {
                scope.launch {
                    val size = DialogService.askForNewTerritorySize()
                    if (size != null) {
                        EditorState.setTerritorySize(size)
                    }
                }
            },
            modifier = Modifier.padding(end = padding),
            true
        ) { Text(ResString.get("editor.appbar.button.change.size")) }
    }
}
