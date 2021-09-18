package de.github.dudrie.hamster.editor.application

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.editor.application.helpers.handleCreateNewTerritory
import de.github.dudrie.hamster.editor.application.helpers.handleOpenTerritory
import de.github.dudrie.hamster.editor.application.helpers.handleSaveTerritory
import de.github.dudrie.hamster.editor.dialog.DialogService
import de.github.dudrie.hamster.i18n.HamsterString
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
        val snackbarHost = SnackbarHostLocal.current

        AppBarButton(
            onClick = { scope.launch { handleCreateNewTerritory() } },
            modifier = Modifier.padding(start = padding, end = padding),
        ) { Text(HamsterString.get("editor.appbar.button.new")) }

        AppBarButton(
            onClick = { scope.launch { handleSaveTerritory(snackbarHost) } },
            enabled = EditorState.hasStartingHamster,
            modifier = Modifier.padding(end = padding)
        ) { Text(HamsterString.get("editor.appbar.button.save")) }

        AppBarButton(
            onClick = { scope.launch { handleOpenTerritory(snackbarHost) } },
            modifier = Modifier.padding(end = padding),
        ) { Text(HamsterString.get("editor.appbar.button.open")) }

        AppBarButton(
            onClick = {
                scope.launch {
                    DialogService.askForNewTerritorySize()?.let { result ->
                        EditorState.setTerritorySize(result.size)
                        EditorState.setTerritoryTileToMeterScaling(result.tileToMeterScaling)
                    }
                }
            },
            modifier = Modifier.padding(end = padding),
        ) { Text(HamsterString.get("editor.appbar.button.change.size")) }
    }
}
