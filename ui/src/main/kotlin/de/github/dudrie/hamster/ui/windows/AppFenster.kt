package de.github.dudrie.hamster.ui.windows

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
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import kotlin.system.exitProcess

sealed class AppFenster(private val titel: StringResource) {
    @Composable
    abstract fun FrameWindowScope.inhalt()

    protected fun zeige() {
        CoroutineScope(Dispatchers.Default).launchApplication {
            Window(
                title = stringResource(titel),
                onCloseRequest = {
                    exitApplication()
                    exitProcess(0) // Der Hauptprozess muss ebenfalls beendet werden
                },
                state = WindowState(size = DpSize(1000.dp, 750.dp))
            ) {
                ThemeWrapper {
                    inhalt()
                }
            }
        }
    }
}
