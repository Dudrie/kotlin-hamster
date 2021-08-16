package de.github.dudrie.hamster.ui.editor

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.de.github.dudrie.hamster.interfaces.AbstractEditableTerritory
import de.github.dudrie.hamster.ui.components.board.BoardGrid
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


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
        BoardGrid(territory, 1.dp) { location, tileModifier ->
            var editedTile by EditorState.editedTile
            val interactionSource = remember { MutableInteractionSource() }

            LaunchedEffect(interactionSource) {
                interactionSource.interactions.collect {
                    if (it is PressInteraction.Release) {
                        launch {
                            editedTile = EditedTile(location)
                        }
                    }
                }
            }

            Column(
                tileModifier.clickable(
                    interactionSource = interactionSource,
                    indication = LocalIndication.current,
                    enabled = editedTile?.location != location
                ) { }
            ) {
                Text("$location")

                if (editedTile?.location == location) {
                    Text("GETS EDITED")
                }
            }
        }
    }
}

class EditedTile(val location: Location)

object EditorState {
    val editedTile = mutableStateOf<EditedTile?>(null)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Toolbox(modifier: Modifier = Modifier) {
    Box(modifier) {
        val editedTile by EditorState.editedTile
        val drawerState = remember { MutableTransitionState(false) }

        LaunchedEffect(editedTile) {
            if (drawerState.currentState) {
                delay(200L)
            }
            drawerState.targetState = editedTile != null
        }

        Column(Modifier.background(Color.Yellow).fillMaxSize()) {
            Text("TOOLBOX")
        }

        AnimatedVisibility(
            visibleState = drawerState,
            enter = fadeIn() + slideInHorizontally({ it / 2 }),
            exit = fadeOut() + slideOutHorizontally({ it / 2 })
        ) {
            val scope = rememberCoroutineScope()
            Column(Modifier.fillMaxSize().background(Color.Cyan)) {
                if (editedTile != null) {
                    Text("IM EDITING ${editedTile!!.location}")
                }

                Button(onClick = {
                    scope.launch {
                        drawerState.targetState = false
                        EditorState.editedTile.value = null
                    }
                }) {
                    Text("CLOSE")
                }
            }
        }
    }
}
