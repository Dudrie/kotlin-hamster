package de.github.dudrie.hamster.editor.sidepanel

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.datatypes.Richtung
import de.github.dudrie.hamster.editor.components.textfield.TextFieldForIntegers
import de.github.dudrie.hamster.editor.components.textfield.rememberTextFieldForNumbersState
import de.github.dudrie.hamster.editor.i18n.EditorString
import de.github.dudrie.hamster.editor.model.EditableHamster
import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.internal.model.territory.GameTileContent
import de.github.dudrie.hamster.ui.components.Select

/**
 * Edit panel that allows to change the configuration of the given [content].
 *
 * The layout of the [EditPanelTileContent] depends on the type of the [content].
 */
@Composable
fun EditPanelTileContent(content: GameTileContent) {
    if (content is EditableHamster) {
        EditPanelHamsterTileContent(content)
    }
}

/**
 * Panel for the editor that allows to adjust the configuration of the hamster.
 */
@Composable
fun EditPanelHamsterTileContent(content: EditableHamster) {
    val grainCountState = rememberTextFieldForNumbersState(content.grainCount, content) { newValue, state ->
        if (newValue >= 0) {
            content.setGrainCount(newValue)
        }
        state.isError = newValue < 0
    }

    Text(
        EditorString.get("editor.side.edit.hamster.title"),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 16.dp)) {
        Text("${EditorString.get("editor.side.edit.hamster.direction.of.view")}:", Modifier.padding(end = 8.dp))
        Select(
            items = remember {
                listOf(
                    Richtung.Norden,
                    Richtung.Osten,
                    Richtung.Sueden,
                    Richtung.Westen
                )
            },
            itemToString = { HamsterString.getForDirection(it) },
            value = content.direction,
            onValueChanged = { content.setDirection(it) }
        )
    }

    TextFieldForIntegers(
        state = grainCountState,
        label = { Text(EditorString.get("editor.side.edit.hamster.grain.count.label")) },
        hint = EditorString.getWithFormat("editor.side.edit.hamster.grain.count.hint", content.grainCount)
    )
}
