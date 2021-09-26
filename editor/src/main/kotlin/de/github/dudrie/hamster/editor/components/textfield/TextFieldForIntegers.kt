package de.github.dudrie.hamster.editor.components.textfield

import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Special [TextField] that helps handle inputs which should be [integers][Int].
 *
 * For a description of the parameters see [TextFieldForNumbers].
 *
 * @see [TextFieldForNumbers]
 */
@Composable
fun TextFieldForIntegers(
    state: TextFieldForNumbersState<Int>,
    label: @Composable () -> Unit,
    enabled: Boolean = true,
    hint: String? = null,
    modifier: Modifier = Modifier
) {
    TextFieldForNumbers(
        state = state,
        parseValue = { it.toIntOrNull() },
        label = label,
        enabled = enabled,
        hint = hint,
        modifier = modifier
    )
}
