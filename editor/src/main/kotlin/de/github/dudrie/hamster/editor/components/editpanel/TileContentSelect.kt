package de.github.dudrie.hamster.editor.components.editpanel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.commons.select.Select
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.*
import de.github.dudrie.hamster.editor.generated.tile_content_select_type_floor
import de.github.dudrie.hamster.editor.generated.tile_content_select_type_grains
import de.github.dudrie.hamster.editor.generated.tile_content_select_type_wall
import de.github.dudrie.hamster.ui.generated.Res
import de.github.dudrie.hamster.ui.generated.floor
import de.github.dudrie.hamster.ui.generated.grain
import de.github.dudrie.hamster.ui.generated.wall
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import de.github.dudrie.hamster.editor.generated.Res as EditorRes

@Composable
fun TileContentSelect(
    tile: Kachel,
    hamster: InternerHamster?,
    onChange: (Kachelinhalt) -> Unit,
    modifier: Modifier = Modifier
) {
    Select(
        items = listOf(Leer, Wand, KornInhalt(0)),
        itemToContent = {
            when (it) {
                Leer -> {
                    Image(
                        painterResource(Res.drawable.floor),
                        null,
                        modifier = Modifier.size(40.dp).padding(end = 8.dp)
                    )
                    Text(stringResource(EditorRes.string.tile_content_select_type_floor))
                }

                Wand -> {
                    Image(
                        painterResource(Res.drawable.wall),
                        null,
                        modifier = Modifier.size(40.dp).padding(end = 8.dp)
                    )
                    Text(stringResource(EditorRes.string.tile_content_select_type_wall))
                }

                is KornInhalt -> {
                    Image(
                        painterResource(Res.drawable.grain),
                        null,
                        modifier = Modifier.size(40.dp).padding(end = 8.dp)
                    )
                    Text(stringResource(EditorRes.string.tile_content_select_type_grains))
                }
            }
        },
        isItemEnabled = {
            when (it) {
                Wand -> hamster == null

                Leer, is KornInhalt -> true
            }
        },
        value = tile.inhalt,
        onValueChanged = {
            val newContent = when (it) {
                Leer -> Leer
                Wand -> Wand
                is KornInhalt -> KornInhalt(1)
            }
            onChange(newContent)
        },
        modifier = modifier
    )
}
