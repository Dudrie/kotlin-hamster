package de.github.dudrie.hamster.editor.dialog

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.github.dudrie.hamster.datatypes.Size
import kotlinx.coroutines.CompletableDeferred

/**
 * Handles the state used by the [DialogManager].
 */
internal object DialogService {
    /**
     * Dialog to show.
     */
    val dialog = mutableStateOf<@Composable (() -> Unit)?>(null)

    /**
     * Shows the given dialog.
     */
    private fun showDialog(dialog: @Composable () -> Unit) {
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
    suspend fun askForNewTerritorySize(): Size? {
        val state = DialogState<Size?>()

        showDialog {
            SizeChangeDialog(onSizeAccept = {
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
     * Show a confirmation dialog to the user.
     */
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
 * A state used to abstract logic while waiting on a response from a dialog.
 */
private class DialogState<T> {
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
