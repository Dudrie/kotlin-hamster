package de.github.dudrie.hamster.editor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
        ) { Text(ResString.get("editor.appbar.button.new")) }

        OutlinedButton(
            { TODO("Not implemented") },
            colors = ControlButtonColors,
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
            modifier = Modifier.padding(end = padding)
        ) { Text(ResString.get("editor.appbar.button.save")) }

        OutlinedButton(
            { TODO("Not implemented") },
            colors = ControlButtonColors,
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
            modifier = Modifier.padding(end = padding)
        ) { Text(ResString.get("editor.appbar.button.open")) }

        OutlinedButton(
            { DialogState.showDialog { SizeChangeDialog(onSizeAccept = { EditorState.setTerritorySize(it) }) } },
            colors = ControlButtonColors,
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
            modifier = Modifier.padding(end = padding)
        ) { Text(ResString.get("editor.appbar.button.change.size")) }
    }
}

/**
 * Special dialog in which the user can enter a new size.
 *
 * The text fields only allow values up to the [maximum value][EditorState.maxColumnAndRowCount] defined in [EditorState].
 */
@Composable
fun SizeChangeDialog(onSizeAccept: (size: Size) -> Unit) {
    val columnState = rememberTextFieldForNumbersState(EditorState.territory.value.territorySize.columnCount)
    val rowState = rememberTextFieldForNumbersState(EditorState.territory.value.territorySize.rowCount)

    LaunchedEffect(columnState.value) {
        columnState.isError = columnState.value > EditorState.maxColumnAndRowCount
    }

    LaunchedEffect(rowState.value) {
        rowState.isError = rowState.value > EditorState.maxColumnAndRowCount
    }

    DefaultDialog(
        onDismissRequest = DialogState::dismissDialog,
        title = { Text(ResString.get("editor.dialog.change.size.title")) },
        text = {
            Column {
                Text(ResString.get("editor.dialog.change.size.text"))

                Row(Modifier.padding(top = 16.dp)) {
                    TextFieldForNumbers(
                        state = columnState,
                        label = { Text(ResString.get("editor.dialog.change.size.text.field.columns.label")) })

                    Spacer(Modifier.width(32.dp))

                    TextFieldForNumbers(
                        state = rowState,
                        label = { Text(ResString.get("editor.dialog.change.size.text.field.rows.label")) })
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
                content = { Text(ResString.get("editor.dialog.change.size.confirm")) },
                enabled = !columnState.isError && !rowState.isError
            )
        }
    )
}
