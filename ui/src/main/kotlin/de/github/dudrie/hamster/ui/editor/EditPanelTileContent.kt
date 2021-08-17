package de.github.dudrie.hamster.ui.editor

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import de.github.dudrie.hamster.ResString
import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.internal.model.hamster.EditableHamster
import de.github.dudrie.hamster.internal.model.territory.GameTileContent
import de.github.dudrie.hamster.ui.components.Select
import de.github.dudrie.hamster.ui.components.TextFieldForNumbers

/**
 * Edit panel that allows to change the configuration of the given [content].
 *
 * The layout of the [EditPanelTileContent] depends on the type of the [content].
 */
@Composable
fun EditPanelTileContent(content: GameTileContent) {
    if (content is EditableHamster) {
        Text("EDIT A HAMSTER", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

        Select(
            items = remember {
                listOf(
                    Direction.North,
                    Direction.East,
                    Direction.South,
                    Direction.West
                )
            },
            itemToString = { ResString.getForDirection(it) },
            value = content.direction,
            onValueChanged = { content.setDirection(it) }
        )

        TextFieldForNumbers(
            value = content.grainCount,
            onValueChanged = {
                if (it > 0) {
                    content.setGrainCount(it)
                }
            },
            label = { Text("GRAIN COUNT") },
            hint = "GRAIN COUNT OF HAMSTER: ${content.grainCount}"
        )
    }
}
