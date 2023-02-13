package de.github.dudrie.hamster.editor.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.editor.application.EditorState
import de.github.dudrie.hamster.editor.components.textfield.TextFieldForDoubles
import de.github.dudrie.hamster.editor.components.textfield.TextFieldForIntegers
import de.github.dudrie.hamster.editor.components.textfield.rememberTextFieldForNumbersState
import de.github.dudrie.hamster.editor.i18n.EditorString

/**
 * Special dialog in which the user can enter a new size.
 *
 * The text fields only allow values up to the [maximum value][EditorState.maxColumnAndRowCount] defined in [EditorState].
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SizeChangeDialog(onAccept: (result: SizeChangeDialogResult) -> Unit, onDismiss: () -> Unit) {
    val columnState = rememberTextFieldForNumbersState(EditorState.territory.value.abmessungen.columnCount)
    val rowState = rememberTextFieldForNumbersState(EditorState.territory.value.abmessungen.rowCount)
    val scalingState = rememberTextFieldForNumbersState(EditorState.territory.value.feldZuMeterSkalierung)

    LaunchedEffect(columnState.value) {
        columnState.isError = columnState.value > EditorState.maxColumnAndRowCount
    }

    LaunchedEffect(rowState.value) {
        rowState.isError = rowState.value > EditorState.maxColumnAndRowCount
    }

    LaunchedEffect(scalingState.value) {
        scalingState.isError = scalingState.value < 0
    }

    DefaultDialog(
        onDismissRequest = onDismiss,
        title = { Text(EditorString.get("editor.dialog.change.size.title")) },
        text = {
            Column {
                Text(EditorString.get("editor.dialog.change.size.text"))

                Row(Modifier.padding(top = 16.dp)) {
                    TextFieldForIntegers(
                        state = columnState,
                        label = { Text(EditorString.get("editor.dialog.change.size.text.field.columns.label")) })

                    Spacer(Modifier.width(32.dp))

                    TextFieldForIntegers(
                        state = rowState,
                        label = { Text(EditorString.get("editor.dialog.change.size.text.field.rows.label")) })
                }

                Row(Modifier.padding(top = 8.dp)) {
                    TextFieldForDoubles(
                        state = scalingState,
                        label = { Text(EditorString.get("editor.dialog.change.size.text.field.scaling.label")) }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAccept(SizeChangeDialogResult(Size(columnState.value, rowState.value), scalingState.value))
                },
                content = { Text(EditorString.get("editor.dialog.change.size.confirm")) },
                enabled = !columnState.isError && !rowState.isError && !scalingState.isError
            )
        }
    )
}

/**
 * The final result of a [SizeChangeDialog] if the user accepts the changes.
 *
 * @param size The [Size] the user entered.
 * @param tileToMeterScaling The amount of meters a single tile represents in the territory as entered by the user.
 */
data class SizeChangeDialogResult(val size: Size, val tileToMeterScaling: Double)
