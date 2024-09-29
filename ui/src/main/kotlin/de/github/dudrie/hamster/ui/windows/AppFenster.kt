package de.github.dudrie.hamster.ui.windows

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.launchApplication
import androidx.lifecycle.Lifecycle
import de.github.dudrie.hamster.ui.theme.ThemeWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Semaphore
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import kotlin.system.exitProcess

sealed class AppFenster(private val titel: StringResource) {
    @Composable
    abstract fun FrameWindowScope.inhalt()

    protected fun zeige(latch: Semaphore) {
        CoroutineScope(Dispatchers.Default).launchApplication {
            Window(
                title = stringResource(titel),
                onCloseRequest = {
                    exitApplication()
                    exitProcess(0) // Der Hauptprozess muss ebenfalls beendet werden
                },
                state = WindowState(size = DpSize(1000.dp, 750.dp))
            ) {
                val lifecycleOwner = LocalLifecycleOwner.current
                val lifecycle by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

                LaunchedEffect(lifecycle) {
                    when (lifecycle) {
                        Lifecycle.State.RESUMED -> {
                            if (latch.availablePermits == 0) {
                                delay(1000) // Warte, bis das Fenster aufgetaucht ist
                                latch.release()
                            }
                        }

                        else -> {}
                    }
                }

                ThemeWrapper {
                    inhalt()
                }
            }
        }
    }
}
