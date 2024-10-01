package de.github.dudrie.hamster.editor.components.appbar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.editor.DialogState
import de.github.dudrie.hamster.editor.generated.Res
import de.github.dudrie.hamster.editor.generated.button_load
import de.github.dudrie.hamster.editor.generated.button_new
import de.github.dudrie.hamster.editor.generated.button_save
import de.github.dudrie.hamster.editor.model.EditorUIState
import de.github.dudrie.hamster.ui.components.appbar.AppBarButton
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun EditorAppBar(dialogState: DialogState = viewModel(), editorState: EditorUIState = viewModel()) {
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.height(64.0.dp).fillMaxWidth(),
        shadowElevation = 24.dp,
        color = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier.padding(start = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AppBarButton(onClick = {
                scope.launch {
                    if (dialogState.askToCreateNewFile()) {
                        editorState.createNewTerritory()
                    }
                }
            }) { Text(stringResource(Res.string.button_new)) }

            AppBarButton(
                onClick = {
                    scope.launch {
                        dialogState.askForFileToSave()?.let {
                            editorState.saveToFile(it)
                        }
                    }
                },
                enabled = editorState.hamster.isNotEmpty()
            ) { Text(stringResource(Res.string.button_save)) }

            AppBarButton(onClick = {
                scope.launch {
                    dialogState.askForFileToLoad()?.let {
                        editorState.loadFromFile(it)
                    }
                }
            }) { Text(stringResource(Res.string.button_load)) }
        }
    }
}
