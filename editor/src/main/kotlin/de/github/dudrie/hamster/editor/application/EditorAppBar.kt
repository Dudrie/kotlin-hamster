package de.github.dudrie.hamster.editor.application

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
        ) { Text(ResString.get("editor.appbar.button.new")) }

        AppBarButton(
            onClick = {
                scope.launch { DialogService.askForFileToSave()?.let { path -> EditorState.saveToFile(path) } }
            },
            modifier = Modifier.padding(end = padding),
        ) { Text(ResString.get("editor.appbar.button.save")) }

        AppBarButton(
            onClick = {
                scope.launch {
                    // TODO: Add confirmation if user really wants to load a new territory -> Old one gets overridden.
                    DialogService.askForFileToLoad()?.let { path -> EditorState.loadFromFile(path) }
                }
            },
            modifier = Modifier.padding(end = padding),
        ) { Text(ResString.get("editor.appbar.button.open")) }

        AppBarButton(
            onClick = {
                scope.launch {
                    DialogService.askForNewTerritorySize()?.let { size -> EditorState.setTerritorySize(size) }
                }
            },
            modifier = Modifier.padding(end = padding),
        ) { Text(ResString.get("editor.appbar.button.change.size")) }
    }
}
