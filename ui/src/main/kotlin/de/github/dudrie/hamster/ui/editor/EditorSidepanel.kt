package de.github.dudrie.hamster.ui.editor

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Composable shown on the right side of the editor.
 *
 * It also is responsible for showing the [EditPanel] if an object gets edited.
 *
 * @param modifier Modifiers applied to the underlying [Surface].
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EditorSidepanel(modifier: Modifier = Modifier) {
    Surface(modifier, elevation = 4.dp) {
        var editedTile by EditorState.editedTile
        val drawerState = remember { MutableTransitionState(false) }

        LaunchedEffect(editedTile) {
            if (drawerState.currentState) {
                delay(200L)
            }
            drawerState.targetState = editedTile != null
        }

        EditorToolbox(Modifier.fillMaxSize())

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
