package de.github.dudrie.hamster.editor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ResString
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.editor.dialog.DefaultDialog
import de.github.dudrie.hamster.editor.dialog.DialogState
import de.github.dudrie.hamster.ui.components.ControlButtonColors
import de.github.dudrie.hamster.ui.components.TextFieldForNumbers
import de.github.dudrie.hamster.ui.components.rememberTextFieldForNumbersState

/**
 * [TopAppBar] for the editor.
 */
@Composable
fun EditorAppBar() {
    TopAppBar(elevation = 8.dp, contentPadding = PaddingValues(horizontal = 16.dp)) {
        val padding = 16.dp

        // TODO: Make abstract AppBarButton which is used here and in ControlButton (:ui).
        OutlinedButton(
            {
                DialogState.showConfirmDialog(
                    text = { Text(ResString.get("editor.dialog.new.territory.text")) },
                    title = { Text(ResString.get("editor.dialog.new.territory.title")) },
                    confirm = { Text(ResString.get("editor.dialog.new.territory.confirm")) },
                    onConfirm = { EditorState.resetTerritory() },
                    dismiss = { Text(ResString.get("button.cancel")) },
                    onDismiss = {}
                )
            },
            colors = ControlButtonColors,
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
            modifier = Modifier.padding(start = padding, end = padding)
        ) { Text("NEW") }

        OutlinedButton(
            { TODO("Not implemented") },
            colors = ControlButtonColors,
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
            modifier = Modifier.padding(end = padding)
        ) { Text("SAVE") }

        OutlinedButton(
            { TODO("Not implemented") },
            colors = ControlButtonColors,
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
            modifier = Modifier.padding(end = padding)
        ) { Text("LOAD") }

        OutlinedButton(
            {
                DialogState.showDialog { SizeChangeDialog(onSizeAccept = { println(it) }) }
            },
            colors = ControlButtonColors,
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
            modifier = Modifier.padding(end = padding)
        ) { Text("CHANGE SIZE") }
    }
}

@Composable
fun SizeChangeDialog(onSizeAccept: (size: Size) -> Unit) {
    val columnState = rememberTextFieldForNumbersState(EditorState.territory.value.territorySize.columnCount)
    val rowState = rememberTextFieldForNumbersState(EditorState.territory.value.territorySize.rowCount)

    DefaultDialog(
        onDismissRequest = DialogState::dismissDialog,
        title = { Text("NEW TERRITORY SIZE") },
        text = {
            Column {
                Text("CHANGE SIZE TO ${columnState.value} x ${rowState.value}")

                Row(Modifier.padding(top = 16.dp)) {
                    TextFieldForNumbers(state = columnState, label = { Text("COLUMNS") })

                    Spacer(Modifier.width(16.dp))

                    TextFieldForNumbers(state = rowState, label = { Text("ROWS") })
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (!columnState.isError && !rowState.isError) {
                        onSizeAccept(Size(columnState.value, rowState.value))
                        DialogState.dismissDialog()
                    }
                },
                content = { Text("CHANGE SIZE") },
                enabled = !columnState.isError && !rowState.isError
            )
        }
    )
}
