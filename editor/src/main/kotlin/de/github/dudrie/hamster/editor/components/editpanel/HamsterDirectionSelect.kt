package de.github.dudrie.hamster.editor.components.editpanel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Navigation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import de.github.dudrie.commons.select.Select
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.util.Richtung
import de.github.dudrie.hamster.editor.generated.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HamsterDirectionSelect(
    hamster: InternerHamster,
    onChange: (Richtung) -> Unit,
    modifier: Modifier = Modifier
) {
    Select(
        items = Richtung.entries.toList(),
        itemToContent = {
            val content: Pair<Float, StringResource> = when (it) {
                Richtung.Norden -> 0f to Res.string.richtung_norden
                Richtung.Osten -> 90f to Res.string.richtung_osten
                Richtung.Sueden -> 180f to Res.string.richtung_sueden
                Richtung.Westen -> 270f to Res.string.richtung_westen
            }

            Image(
                Icons.Rounded.Navigation,
                null,
                modifier = Modifier.size(40.dp).padding(end = 8.dp).rotate(content.first)
            )
            Text(stringResource(content.second))
        },
        value = hamster.richtung,
        onValueChanged = onChange,
        modifier = modifier
    )
}
