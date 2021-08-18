package de.github.dudrie.hamster.editor.dialog

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
     * Show a confirmation dialog to the user.
     */
    @OptIn(ExperimentalMaterialApi::class)
    fun showConfirmDialog(
        text: @Composable () -> Unit,
        onConfirm: () -> Unit,
        onDismiss: () -> Unit,
        title: @Composable (() -> Unit)? = null,
        confirm: @Composable RowScope.() -> Unit,
        dismiss: @Composable RowScope.() -> Unit
    ) {
        showDialog {
            AlertDialog(
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
                        content = confirm,
                        modifier = Modifier.defaultMinSize(minWidth = 48.dp)
                    )
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            onDismiss()
                            dismissDialog()
                        },
                        content = dismiss,
                        modifier = Modifier.defaultMinSize(minWidth = 48.dp)
                    )
                },
                modifier = Modifier.defaultMinSize(minWidth = 560.dp),
                shape = MaterialTheme.shapes.medium
            )
        }
    }
}
