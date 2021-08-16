package de.github.dudrie.hamster.ui.components.console

import androidx.compose.animation.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import de.github.dudrie.hamster.ui.R
import de.github.dudrie.hamster.ui.components.ResourceIcon
import de.github.dudrie.hamster.ui.components.ResourceIconSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Renders a special FAB which is used in the [ConsolePanel] so the user can scroll to the bottom of the message list.
 *
 * @param visible Is the FAB visible?
 * @param onClick Gets called if the user clicks on the button. For convenience this gets executed inside a coroutine. This is done so one can easily call any list scroll position manipulating functions.
 * @param modifier Modifiers which get applied to the wrapping [AnimatedVisibility] composable.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScrollDownButton(visible: Boolean, onClick: suspend CoroutineScope.() -> Unit, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = fadeIn() + slideInHorizontally({ it / 2 }),
        exit = fadeOut() + slideOutHorizontally({ it / 2 })
    ) {
        val scope = rememberCoroutineScope()

        FloatingActionButton(
            onClick = {
                scope.launch {
                    onClick()
                }
            },
            backgroundColor = MaterialTheme.colors.primary
        ) {
            ResourceIcon(R.icons.scrollDown, size = ResourceIconSize.Medium)
        }
    }
}
