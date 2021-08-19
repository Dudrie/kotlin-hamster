package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

/**
 * Special [TextField] that helps handle inputs which should be numbers.
 *
 * @param value Current value
 * @param onValueChanged Gets called with the new value if the entered value is a valid [Int].
 * @param label Is shown as [label].
 * @param enabled Is this [TextField] currently enabled?
 * @param hint Optional text which gets shown below the [TextField] if provided.
 * @param modifier Modifier that gets applied to the underlying [Column].
 */
@Composable
fun TextFieldForNumbers(
    value: Int,
    onValueChanged: (Int) -> Unit,
    label: @Composable () -> Unit,
    enabled: Boolean = true,
    hint: String? = null,
    isError: Boolean = false,
    onError: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        var textFieldState by remember(value) { mutableStateOf(value.toString()) }

        TextField(
            value = textFieldState,
            onValueChange = { newValue ->
                textFieldState = newValue
                val parsedValue = newValue.toIntOrNull()

                if (parsedValue == null) {
                    onError()
                } else {
                    onValueChanged(parsedValue)
                }
            },
            label = label,
            maxLines = 1,
            isError = isError,
            enabled = enabled
        )

        hint?.let { Text(it) }
    }
}

@Composable
fun TextFieldForNumbers(
    state: TextFieldForNumbersState,
    label: @Composable () -> Unit,
    enabled: Boolean = true,
    hint: String? = null,
    modifier: Modifier = Modifier
) {
    TextFieldForNumbers(
        value = state.value,
        onValueChanged = {
            state.value = it
            state.isError = false
        },
        isError = state.isError,
        onError = { state.isError = true },
        label = label,
        enabled = enabled,
        hint = hint,
        modifier = modifier
    )
}

data class TextFieldForNumbersState(
    private val valueState: MutableState<Int>,
    private val isErrorState: MutableState<Boolean>
) {
    var value: Int
        get() = valueState.value
        set(value) {
            valueState.value = value
        }

    var isError: Boolean
        get() = isErrorState.value
        set(value) {
            isErrorState.value = value
        }
}

@Composable
fun rememberTextFieldForNumbersState(initialValue: Int): TextFieldForNumbersState =
    remember { TextFieldForNumbersState(mutableStateOf(initialValue), mutableStateOf(false)) }
