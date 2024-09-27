package de.github.dudrie.hamster.ui.windows

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.window.FrameWindowScope
import de.github.dudrie.hamster.ui.components.LadeUI
import de.github.dudrie.hamster.ui.generated.Res
import de.github.dudrie.hamster.ui.generated.app_title
import de.github.dudrie.hamster.ui.model.HamsterSpiel
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Semaphore

class SpielFenster(private val importJob: Job, private val spiel: HamsterSpiel) :
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

        LaunchedEffect(importJob) {
            importJob.join()
            ladtSpiel = false
            fensterSemaphore.release()
        }

        Scaffold() {
            if (ladtSpiel) {
                LadeUI()
            } else {

            }
        }
    }

}
