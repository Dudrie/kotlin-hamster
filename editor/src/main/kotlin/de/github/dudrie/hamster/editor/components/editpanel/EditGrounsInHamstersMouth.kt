package de.github.dudrie.hamster.editor.components.editpanel

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.editor.generated.Res
import de.github.dudrie.hamster.editor.generated.edit_panel_tf_hamster_grain_in_mouth
import org.jetbrains.compose.resources.stringResource

@Composable
fun EditGrainsInHamstersMouth(hamster: InternerHamster, onChange: (Int) -> Unit) {
    var value by remember(hamster.kornAnzahl) { mutableStateOf(hamster.kornAnzahl.toString()) }

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            value = newValue

            val count = newValue.toIntOrNull()
            if (count != null && count >= 0) {
                onChange(count)
            }
        },
        label = { Text(stringResource(Res.string.edit_panel_tf_hamster_grain_in_mouth)) },
        isError = value.toIntOrNull() == null
    )
}
