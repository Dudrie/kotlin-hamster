package de.github.dudrie.hamster.editor.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.editor.generated.*
import de.github.dudrie.hamster.editor.generated.Res
import de.github.dudrie.hamster.editor.generated.edit_panel_bt_hamster_add
import de.github.dudrie.hamster.editor.generated.edit_panel_hamster_title
import de.github.dudrie.hamster.editor.generated.edit_panel_tile_title
import de.github.dudrie.hamster.editor.model.EditorUIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun EditPanel(
    visible: Boolean,
    position: Position?,
    state: EditorUIState = viewModel(),
    modifier: Modifier = Modifier
) {
    var show by remember(visible) { mutableStateOf(visible) }
    val scope = rememberCoroutineScope()

    AnimatedVisibility(
        visible = show,
        enter = slideIn { IntOffset(it.width, 0) },
        exit = slideOut { IntOffset(it.width, 0) } + fadeOut()
    ) {
        if (position == null) return@AnimatedVisibility

        val tile = state.getTileAt(position)
        val hamster = state.getHamsterAt(position)

        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(modifier) {
                Row {
                    Text(
                        text = stringResource(Res.string.edit_panel_tile_title),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = {
                        scope.launch {
                            show = false
                            delay(500)
                            state.selectedPosition = null
                        }
                    }) {
                        Icon(Icons.Rounded.Close, null)
                    }
                }

                // TODO: Kachelinhalt bearbeiten:
                //       Typ festlegen und abhängig davon ein entsprechendes Panel anzeigen

                if (hamster == null) {
                    OutlinedButton(
                        onClick = { state.createHamsterAt(position) },
                        modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
                    ) {
                        Text(stringResource(Res.string.edit_panel_bt_hamster_add))
                    }
                }

                hamster?.let {
                    HorizontalDivider(Modifier.padding(vertical = 8.dp))

                    Text(
                        text = stringResource(Res.string.edit_panel_hamster_title),
                        style = MaterialTheme.typography.headlineSmall
                    )

                    // TODO: Hamsterrichtung festlegen
                    // TODO: Anzahl Körner zu Beginn festlegen

                    OutlinedButton(
                        onClick = { state.removeHamsterFrom(position) },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
                    ) {
                        Text(stringResource(Res.string.edit_panel_bt_hamster_remove))
                    }
                }
            }
        }
    }
}
