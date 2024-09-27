package de.github.dudrie.hamster.ui.windows

import androidx.compose.runtime.*
import androidx.compose.ui.window.FrameWindowScope
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.core.game.SpielViewModel
import de.github.dudrie.hamster.ui.components.util.LoadingUI
import de.github.dudrie.hamster.ui.components.util.WindowContent
import de.github.dudrie.hamster.ui.generated.Res
import de.github.dudrie.hamster.ui.generated.app_title
import de.github.dudrie.hamster.ui.model.UIViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Semaphore

class SpielFenster(private val importJob: Job, private val spiel: SpielViewModel) :
    AppFenster(Res.string.app_title) {

    /**
     * Wird benutzt, um sicherzustellen, dass das [starte] erste abgeschlossen werden kann, wenn der [importJob] beendet **und** das Fenster für den Hamster-Simulator geladen ist.
     */
    private val fensterSemaphore = Semaphore(permits = 1, acquiredPermits = 1)

    fun starte() {
        runBlocking {
            zeige()
            fensterSemaphore.acquire()
            importJob.join()
        }
    }

    @Composable
    override fun FrameWindowScope.inhalt() {
        var ladtSpiel by remember { mutableStateOf(true) }
        val viewModel = viewModel { UIViewModel(spiel) }

        LaunchedEffect(importJob) {
            importJob.join()
            ladtSpiel = false
            fensterSemaphore.release()
        }

        if (ladtSpiel) {
            LoadingUI()
        } else {
            WindowContent(viewModel)
        }
    }
}
