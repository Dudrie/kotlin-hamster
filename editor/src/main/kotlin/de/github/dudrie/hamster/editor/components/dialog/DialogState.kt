package de.github.dudrie.hamster.editor.components.dialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.lifecycle.ViewModel
import de.github.dudrie.commons.forms.components.FormTextField
import de.github.dudrie.hamster.core.model.territory.Abmessungen
import de.github.dudrie.hamster.editor.generated.*
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import java.nio.file.Path

class DialogState : ViewModel() {

    var dialog by mutableStateOf<@Composable (FrameWindowScope.() -> Unit)?>(null)
        private set

    suspend fun askToCreateNewFile(): Boolean = askForConfirmation(
        title = getString(Res.string.dialog_confirm_create_new_title),
        text = getString(Res.string.dialog_confirm_create_new_text),
        confirmText = getString(Res.string.button_create_new),
        dismissText = getString(Res.string.button_cancel)
    )

    private suspend fun askForConfirmation(
        title: String,
        text: String,
        confirmText: String,
        dismissText: String
    ): Boolean {
        val result = DialogResult<Boolean>()
        dialog = {
            AlertDialog(
                onDismissRequest = {
                    result.setResult(false)
                    dialog = null
                },
                confirmButton = {
                    Button(onClick = {
                        result.setResult(true)
                        dialog = null
                    }) { Text(confirmText) }
                },
                dismissButton = {
                    TextButton(onClick = {
                        result.setResult(false)
                        dialog = null
                    }) { Text(dismissText) }
                },
                title = { Text(title) },
                text = { Text(text) })
        }
        return result.awaitResult()
    }

    suspend fun askForFileToSave(): Path? {
        val result = DialogResult<Path?>()
        dialog = {
            FileDialog(title = stringResource(Res.string.file_dialog_save_title), isLoad = false) {
                result.setResult(it)
                dialog = null
            }
        }
        return result.awaitResult()
    }

    suspend fun askForFileToLoad(): Path? {
        val state = DialogResult<Path?>()
        dialog = {
            FileDialog(title = stringResource(Res.string.file_dialog_load_title), isLoad = true) {
                state.setResult(it)
                dialog = null
            }
        }
        return state.awaitResult()
    }

    suspend fun askForNewTerritorySize(initialSize: Abmessungen): Abmessungen? {
        val state = DialogResult<Abmessungen?>()
        val form = TerritorySizeFormState(initialSize)

        dialog = {
            AlertDialog(
                onDismissRequest = {
                    state.setResult(null)
                    dialog = null
                },
                confirmButton = {
                    Button(onClick = {
                        state.setResult(
                            Abmessungen(
                                breite = form.width.value,
                                hohe = form.height.value
                            )
                        )
                        dialog = null
                    }, enabled = form.isValid) { Text("Größe ändern") }
                },
                dismissButton = {
                    TextButton(onClick = {
                        state.setResult(null)
                        dialog = null
                    }) { Text(stringResource(Res.string.button_cancel)) }
                },
                title = { Text("Territoriumsgröße ändern") },
                text = {
                    Row {
                        FormTextField(
                            value = form.width,
                            label = { Text("Breite") },
                            isInitiallyFocused = true,
                            isSelectAllOnFocs = true,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(Modifier.width(24.dp))

                        FormTextField(
                            value = form.height,
                            label = { Text("Höhe") },
                            isSelectAllOnFocs = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            )
        }

        return state.awaitResult()
    }

}
