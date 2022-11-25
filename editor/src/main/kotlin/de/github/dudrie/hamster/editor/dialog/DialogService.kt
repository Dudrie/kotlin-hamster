package de.github.dudrie.hamster.editor.dialog

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.FrameWindowScope
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.i18n.HamsterString
import java.nio.file.Path

/**
 * Handles the state used by the [DialogManager].
 */
internal object DialogService {
    /**
     * Dialog to show.
     */
    val dialog = mutableStateOf<@Composable (FrameWindowScope.() -> Unit)?>(null)

    /**
     * Shows the given dialog.
     */
    private fun showDialog(dialog: @Composable FrameWindowScope.() -> Unit) {
        this.dialog.value = dialog
    }

    /**
     * Dismisses the current dialog.
     */
    private fun dismissDialog() {
        dialog.value = null
    }

    /**
     * Asks the user to confirm an action.
     *
     * Returns the [ConfirmDialogResult] according to the selection of the user.
     *
     * @param text Text to show in the dialog.
     * @param title Title of the dialog.
     * @param confirm Content of the "confirm" button.
     * @param dismiss Content of the "dismiss" button.
     */
    suspend fun askForConfirmation(
        text: @Composable () -> Unit,
        title: @Composable (() -> Unit)? = null,
        confirm: @Composable RowScope.() -> Unit,
        dismiss: @Composable RowScope.() -> Unit
    ): ConfirmDialogResult {
        val internalState = DialogState<ConfirmDialogResult>()
        showConfirmDialog(text = text, title = title, confirm = confirm, dismiss = dismiss, onConfirm = {
            internalState.setResult(ConfirmDialogResult.Confirm)
        }, onDismiss = {
            internalState.setResult(ConfirmDialogResult.Dismiss)
        })

        return internalState.awaitResult()
    }

    /**
     * Shows a dialog to the user asking for the new size of the territory.
     *
     * Returns the entered [Size] or `null` if the dialog is dismissed.
     */
    suspend fun askForNewTerritorySize(): SizeChangeDialogResult? {
        val state = DialogState<SizeChangeDialogResult?>()

        showDialog {
            SizeChangeDialog(onAccept = {
                dismissDialog()
                state.setResult(it)
            }, onDismiss = {
                dismissDialog()
                state.setResult(null)
            })
        }

        return state.awaitResult()
    }

    /**
     * Shows the user a dialog to pick a file to open.
     *
     * Returns the selected [Path] or `null` if no path got selected.
     */
    suspend fun askForFileToLoad(): Path? =
        showFileDialog(title = HamsterString.get("dialog.file.load.title"), isOpenFile = true)

    /**
     * Shows the user a dialog to pick a path to save to.
     *
     * Returns the selected [Path] or `null` if no path got selected.
     */
    suspend fun askForFileToSave(): Path? =
        showFileDialog(title = HamsterString.get("dialog.file.save.title"), isOpenFile = false)

    /**
     * Helper function showing a dialog for file handling.
     *
     * The dialog will have the given [title] and is either an opening of a saving dialog depending on [isOpenFile]. The chosen file path will get returned or `null` if the user did not select any path.
     */
    private suspend fun showFileDialog(title: String, isOpenFile: Boolean): Path? {
        val state = DialogState<Path?>()
        showDialog {
            FileDialog(title = title, isOpenFile = isOpenFile, onFileSelection = {
                state.setResult(it)
                dismissDialog()
            })
        }
        return state.awaitResult()
    }

    /**
     * Show a confirmation dialog to the user.
     */
    @OptIn(ExperimentalMaterialApi::class)
    private fun showConfirmDialog(
        text: @Composable () -> Unit,
        onConfirm: () -> Unit,
        onDismiss: () -> Unit,
        title: @Composable (() -> Unit)? = null,
        confirm: @Composable RowScope.() -> Unit,
        dismiss: @Composable RowScope.() -> Unit
    ) {
        showDialog {
            DefaultDialog(
                onDismissRequest = {
                    onDismiss()
                    dismissDialog()
                },
                text = text,
                title = title,
                confirmButton = {
                    TextButton(
                        onClick = {
                            onConfirm()
                            dismissDialog()
                        },
                        content = confirm
                    )
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            onDismiss()
                            dismissDialog()
                        },
                        content = dismiss
                    )
                }
            )
        }
    }
}

/**
 * Result of a confirm dialog.
 */
enum class ConfirmDialogResult {
    /**
     * User clicked the "confirm" button.
     */
    Confirm,

    /**
     * User clicked the "dismiss" button.
     */
    Dismiss
}
