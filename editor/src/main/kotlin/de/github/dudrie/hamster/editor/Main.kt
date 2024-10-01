package de.github.dudrie.hamster.editor

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.editor.components.EditorContent
import de.github.dudrie.hamster.editor.components.appbar.EditorAppBar
import de.github.dudrie.hamster.editor.generated.*
import de.github.dudrie.hamster.editor.generated.Res
import de.github.dudrie.hamster.editor.generated.editor_app_title
import de.github.dudrie.hamster.editor.generated.file_dialog_load_title
import de.github.dudrie.hamster.editor.generated.file_dialog_save_title
import de.github.dudrie.hamster.ui.theme.ThemeWrapper
import kotlinx.coroutines.CompletableDeferred
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import java.awt.FileDialog
import java.io.File
import java.nio.file.Path

fun main() {
    //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

    application {
        Window(
            title = stringResource(Res.string.editor_app_title),
            onCloseRequest = ::exitApplication,
            state = WindowState(size = DpSize(1000.dp, 750.dp))
        ) {
            ThemeWrapper {
                Scaffold(topBar = { EditorAppBar() }) { innerPadding ->
                    EditorContent(Modifier.padding(innerPadding))
                }
            }

            DialogPlacer()
        }
    }
}

class DialogResult<T> {
    /**
     * Handles the waiting and storing the result.
     */
    private var onResult: CompletableDeferred<T>? by mutableStateOf(null)

    /**
     * Waits until a result is set via [setResult].
     */
    suspend fun awaitResult(): T {
        onResult = onResult ?: CompletableDeferred()
        val result = onResult!!.await()
        onResult = null
        return result
    }

    /**
     * Sets the [result] of this state if there is at least one awaiting the result.
     *
     * Makes [awaitResult] return with the given [result].
     */
    fun setResult(result: T) {
        onResult?.complete(result)
    }
}

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
                    TextButton(onClick = {
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

}

@Composable
fun FrameWindowScope.DialogPlacer(state: DialogState = viewModel()) {
    state.dialog?.let { it() }
}

@Composable
fun FrameWindowScope.FileDialog(
    title: String,
    isLoad: Boolean,
    onSelectFile: (path: Path?) -> Unit
) {
    AwtWindow(
        create = {
            object : FileDialog(window, title, if (isLoad) LOAD else SAVE) {
                override fun setVisible(visible: Boolean) {
                    super.setVisible(visible)
                    if (visible) {
                        if (file != null) {
                            onSelectFile(File(directory).resolve(file).toPath())
                        } else {
                            onSelectFile(null)
                        }
                    }
                }
            }
        },
        dispose = FileDialog::dispose
    )
}
