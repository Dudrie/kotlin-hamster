package de.github.dudrie.hamster.editor.components.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

/**
 * Special [TextField] that helps handle inputs which should be numbers.
 *
 * @param state [TextFieldForNumbersState] representing the state of this text field.
 * @param parseValue Function that parses the current input to a number. If the current input value is not a valid number `null` should be returned.
 * @param label Is shown as [label].
 * @param enabled Is this [TextField] currently enabled?
 * @param hint Optional text which gets shown below the [TextField] if provided.
 * @param modifier Modifier that gets applied to the underlying [Column].
 */
@Composable
fun <T : Number> TextFieldForNumbers(
    state: TextFieldForNumbersState<T>,
    parseValue: (value: String) -> T?,
    label: @Composable () -> Unit,
    enabled: Boolean = true,
    hint: String? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        var textFieldState by remember(state) { mutableStateOf(state.value.toString()) }

        TextField(
            value = textFieldState,
            onValueChange = { newValue ->
                textFieldState = newValue.replace(",", ".")
                val parsedValue = parseValue(newValue)

                if (parsedValue == null) {
                    state.isError = true
                } else {
                    state.isError = false
                    state.value = parsedValue
                }
            },
            label = label,
            maxLines = 1,
            isError = state.isError,
            enabled = enabled
        )

        hint?.let { Text(it) }
    }
}

/**
 * State representing the current state of a [TextFieldForIntegers].
 *
 * One can create a remembered instance with the [rememberTextFieldForNumbersState] function.
 *
 * @param valueState State of the text field's value.
 * @param isErrorState State that indicates if the text field is in an erroneous state.
 */
data class TextFieldForNumbersState<T : Number>(
    private val valueState: MutableState<T>,
    private val isErrorState: MutableState<Boolean>,
    private val onValueChange: ((newValue: T, state: TextFieldForNumbersState<T>) -> Unit)? = null
) {
    /**
     * Current value of the text field.
     *
     * If the text field is in an erroneous state this is the last **valid** value the text field had.
     */
    var value: T
        get() = valueState.value
        set(value) {
            valueState.value = value
            onValueChange?.invoke(valueState.value, this)
        }

    /**
     * Is true if the text field is in an erroneous state (ie the entered text is not parseable to an [Int]).
     */
    var isError: Boolean
        get() = isErrorState.value
        set(value) {
            isErrorState.value = value
        }
}

/**
 * Creates and returns a new [TextFieldForNumbersState] for with the [initialValue].
 *
 * The created state is [remembered][remember] before being returned.
 *
 * @param initialValue The value to initialize the [TextFieldForNumbersState] with.
 * @param keys List of keys which upon change trigger the recreation of the state object.
 * @param onValueChange (optional) Gets called if the value of the state changes.
 */
@Composable
fun <T : Number> rememberTextFieldForNumbersState(
    initialValue: T,
    vararg keys: Any?,
    onValueChange: ((newValue: T, state: TextFieldForNumbersState<T>) -> Unit)? = null
): TextFieldForNumbersState<T> =
    remember(*keys) {        TextFieldForNumbersState(mutableStateOf(initialValue), mutableStateOf(false), onValueChange)    }

///**
// * Creates and returns a new [TextFieldForNumbersState] for with the [initialValue].
// *
// * The created state is [remembered][remember] before being returned.
// *
// * @param initialValue The value to initialize the [TextFieldForNumbersState] with.
// * @param onValueChange (optional) Gets called if the value of the state changes.
// */
//@Composable
//fun <T : Number> rememberTextFieldForNumbersState(
//    initialValue: T,
//    onValueChange: ((newValue: T, state: TextFieldForNumbersState<T>) -> Unit)? = null
//): TextFieldForNumbersState<T> = rememberTextFieldForNumbersState(initialValue, false, onValueChange)
