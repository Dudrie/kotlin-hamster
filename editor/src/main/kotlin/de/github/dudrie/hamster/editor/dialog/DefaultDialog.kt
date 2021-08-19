package de.github.dudrie.hamster.editor.dialog

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Wrapper for [AlertDialog] which sets some internal defaults.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DefaultDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    dialogProvider: AlertDialogProvider = PopupAlertDialogProvider
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        modifier = modifier.defaultMinSize(minWidth = 560.dp),
        dismissButton = dismissButton,
        title = title,
        text = text,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        dialogProvider = dialogProvider
    )
}
