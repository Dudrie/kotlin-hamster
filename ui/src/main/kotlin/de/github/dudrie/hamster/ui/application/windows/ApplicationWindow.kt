package de.github.dudrie.hamster.ui.application.windows

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.launchApplication
import de.github.dudrie.hamster.ui.theme.ThemeWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.system.exitProcess

/**
 * Helper class which implements the basic functionality to show a Compose window.
 *
 * The rendered content will already be wrapped by [ThemeWrapper].
 */
abstract class ApplicationWindow(private val windowTitle: String) {

    /**
     * Renders the content of the window.
     */
    @Composable
    abstract fun FrameWindowScope.content()

    /**
     * Starts the Compose application.
     *
     * The window renders the composable served by [content] which is wrapped in the [ThemeWrapper] composable.
     */
    fun show() {
        val scope = CoroutineScope(Dispatchers.Default)

        scope.launchApplication {
            Window(
                title = windowTitle,
                onCloseRequest = {
                    exitApplication()
                    exitProcess(0) // Make sure the main process gets halted as well.
                },
                state = WindowState(size = DpSize(1000.dp, 750.dp))
            ) {
                ThemeWrapper {
                    content()
                }
            }
        }
    }
}
