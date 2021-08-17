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
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        var textFieldState by remember(value) { mutableStateOf(value.toString()) }

        TextField(
            value = textFieldState,
            onValueChange = { newValue ->
                textFieldState = newValue
                newValue.toIntOrNull()?.let { onValueChanged(it) }
            },
            label = label,
            maxLines = 1,
            enabled = enabled
        )

        hint?.let { Text(it) }
    }

}
