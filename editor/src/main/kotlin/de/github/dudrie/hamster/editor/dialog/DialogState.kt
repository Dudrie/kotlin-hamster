package de.github.dudrie.hamster.editor.dialog

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

/**
 * Handles the state used by the [DialogManager].
 */
internal object DialogState {
    /**
     * Dialog to show.
     */
    val dialog = mutableStateOf<@Composable (() -> Unit)?>(null)

    /**
     * Shows the given dialog.
     */
    fun showDialog(dialog: @Composable () -> Unit) {
        this.dialog.value = dialog
    }

    /**
     * Dismisses the current dialog.
     */
    fun dismissDialog() {
        dialog.value = null
    }

    /**
     * Show a confirmation dialog to the user.
     */
    fun showConfirmDialog(
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
