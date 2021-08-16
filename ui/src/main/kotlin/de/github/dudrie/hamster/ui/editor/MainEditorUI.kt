package de.github.dudrie.hamster.ui.editor

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.de.github.dudrie.hamster.interfaces.AbstractEditableTerritory
import de.github.dudrie.hamster.ui.components.board.BoardGrid
import kotlinx.coroutines.flow.collect


@Composable
fun MainEditorUI(territory: AbstractEditableTerritory) {
    Scaffold(
        topBar = {
            TopAppBar(elevation = 8.dp, contentPadding = PaddingValues(horizontal = 16.dp)) {
                Text("MENU & TOOLS & SETTINGS", color = MaterialTheme.colors.onPrimary)
            }
        }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            BoardForEditor(territory, Modifier.weight(1f).fillMaxHeight())

            Toolbox(Modifier.fillMaxHeight().width(300.dp).background(Color.Magenta))
        }
    }
}

@Composable
fun BoardForEditor(territory: AbstractEditableTerritory, modifier: Modifier = Modifier) {
    val size = territory.territorySize

    val minWidth = Integer.min(size.columnCount * 32, 300)
    val maxWidth = Integer.max(size.columnCount * 64, 1000)

    Box(
        modifier = modifier.padding(16.dp).widthIn(min = minWidth.dp, max = maxWidth.dp),
        contentAlignment = Alignment.Center
    ) {
        var popupState by mutableStateOf<PopupState?>(null)

        BoardGrid(territory, 1.dp) { location, tileModifier ->
            val interactionSource = remember { MutableInteractionSource() }

            LaunchedEffect(interactionSource) {
                interactionSource.interactions.collect {
                    if (it is PressInteraction.Press) {
                        if (popupState != null && popupState!!.location != location) {
                            popupState = null
                        }
                    }

                    if (it is PressInteraction.Release) {
                        popupState = PopupState(location, it.press.pressPosition)
                    }
                }
            }

            Box(
                tileModifier.clickable(
                    interactionSource = interactionSource,
                    indication = LocalIndication.current,
                    enabled = popupState?.location != location
                ) { }
            ) {
                popupState?.let {
                    if (it.location == location) {
                        val (x, y) = it.offset
                        Popup(
                            offset = IntOffset(x.toInt(), y.toInt()),
                        ) {
                            Surface(
                                Modifier.size(150.dp, 100.dp),
                                elevation = 16.dp,
                                shape = RoundedCornerShape(8.dp),
                            ) {
                                Box(Modifier.padding(8.dp).background(Color.Red)) {
                                    Text("POPUP!!!")
                                }
                            }
                        }
                    }
                }

                Text("$location")
            }
        }
    }
}

data class PopupState(val location: Location, val offset: Offset)

@Composable
fun Toolbox(modifier: Modifier = Modifier) {
    Box(modifier) {
        Text("TOOLBOX")
    }

}
