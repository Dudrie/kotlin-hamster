package de.github.dudrie.hamster.ui.application

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.launchApplication
import de.github.dudrie.hamster.ResString
import de.github.dudrie.hamster.interfaces.IHamsterGame
import de.github.dudrie.hamster.ui.theme.ThemeWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import java.util.concurrent.CountDownLatch
import kotlin.system.exitProcess

internal val HamsterGameLocal = compositionLocalOf<IHamsterGame> { error("No hamster game was provided.") }

class GameWindow(private val hamsterGame: IHamsterGame) {
    fun show(initLatch: CountDownLatch) {
        val scope = CoroutineScope(Dispatchers.Main)

        scope.launchApplication {
            Window(
                title = ResString.get("window.title"),
                onCloseRequest = {
                    exitApplication()
                    exitProcess(0) // Make sure the main process gets halted as well.
                },
                state = WindowState(size = WindowSize(1000.dp, 750.dp))
            ) {
                LaunchedEffect(true) {
                    // Allow the window a short time to run the first render.
                    delay(100L)
                    initLatch.countDown()
                }

                ThemeWrapper {
                    CompositionLocalProvider(
                        HamsterGameLocal provides hamsterGame
                    ) {
                        MainGameUI()
                    }
                }
            }
        }
    }
}
