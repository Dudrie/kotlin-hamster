package de.github.dudrie.hamster.editor.components.editpanel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.core.model.kachel.KornInhalt
import de.github.dudrie.hamster.editor.generated.Res
import de.github.dudrie.hamster.editor.generated.edit_panel_cb_hide_grain_count
import de.github.dudrie.hamster.editor.generated.edit_panel_tf_grain_count
import org.jetbrains.compose.resources.stringResource

@Composable
fun EditGrainCountOnTile(
    inhalt: KornInhalt,
    onCountChanged: (Int) -> Unit,
    onHideGrainChanged: (Boolean) -> Unit
) {
    var value by remember(inhalt.anzahl) { mutableStateOf(inhalt.anzahl.toString()) }

    OutlinedTextField(
        value = value,
        onValueChange = {
            value = it

            if (it.toIntOrNull() != null) {
                onCountChanged(it.toInt())
            }
        },
        label = { Text(stringResource(Res.string.edit_panel_tf_grain_count)) }
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 4.dp).clickable {
            onHideGrainChanged(!inhalt.verbergeKornAnzahl)
        }) {
        Checkbox(
            checked = inhalt.verbergeKornAnzahl,
            onCheckedChange = onHideGrainChanged
        )

        Text(stringResource(Res.string.edit_panel_cb_hide_grain_count))
    }
}
