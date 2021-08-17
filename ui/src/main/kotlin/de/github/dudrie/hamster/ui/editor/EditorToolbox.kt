package de.github.dudrie.hamster.ui.editor

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Toolbox composable which contains different tools to use in the editor.
 *
 * It also is responsible for showing the [EditPanel] if an object gets edited.
 *
 * @param modifier Modifiers applied to the underlying [Box].
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EditorToolbox(modifier: Modifier = Modifier) {
    Box(modifier) {
        var editedTile by EditorState.editedTile
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
            EditPanel(onClose = {
                scope.launch {
                    drawerState.targetState = false
                    editedTile = null
                }
            }, modifier = Modifier.fillMaxSize())
        }
    }
}

