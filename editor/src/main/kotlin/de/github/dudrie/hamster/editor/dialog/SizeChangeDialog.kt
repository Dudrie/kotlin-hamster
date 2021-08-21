package de.github.dudrie.hamster.editor.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.editor.application.EditorState
import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.ui.components.TextFieldForNumbers
import de.github.dudrie.hamster.ui.components.rememberTextFieldForNumbersState

/**
 * Special dialog in which the user can enter a new size.
 *
 * The text fields only allow values up to the [maximum value][EditorState.maxColumnAndRowCount] defined in [EditorState].
 */
@Composable
fun SizeChangeDialog(onSizeAccept: (size: Size) -> Unit, onDismiss: () -> Unit) {
    val columnState = rememberTextFieldForNumbersState(EditorState.territory.value.territorySize.columnCount)
    val rowState = rememberTextFieldForNumbersState(EditorState.territory.value.territorySize.rowCount)

    LaunchedEffect(columnState.value) {
        columnState.isError = columnState.value > EditorState.maxColumnAndRowCount
    }

    LaunchedEffect(rowState.value) {
        rowState.isError = rowState.value > EditorState.maxColumnAndRowCount
    }

    DefaultDialog(
        onDismissRequest = onDismiss,
        title = { Text("editor.dialog.change.size.title") },
        text = {
            Column {
                Text(HamsterString.get("editor.dialog.change.size.text"))

                Row(Modifier.padding(top = 16.dp)) {
                    TextFieldForNumbers(
                        state = columnState,
                        label = { Text(HamsterString.get("editor.dialog.change.size.text.field.columns.label")) })

                    Spacer(Modifier.width(32.dp))

                    TextFieldForNumbers(
                        state = rowState,
                        label = { Text(HamsterString.get("editor.dialog.change.size.text.field.rows.label")) })
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (!columnState.isError && !rowState.isError) {
                        onSizeAccept(Size(columnState.value, rowState.value))
                    }
                },
                content = { Text(HamsterString.get("editor.dialog.change.size.confirm")) },
                enabled = !columnState.isError && !rowState.isError
            )
        }
    )
}
