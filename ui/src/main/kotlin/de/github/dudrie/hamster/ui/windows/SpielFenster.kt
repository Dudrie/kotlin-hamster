package de.github.dudrie.hamster.ui.windows

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.FrameWindowScope
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.core.game.SpielViewModel
import de.github.dudrie.hamster.ui.components.util.LoadingUI
import de.github.dudrie.hamster.ui.components.util.WindowContent
import de.github.dudrie.hamster.ui.generated.Res
import de.github.dudrie.hamster.ui.generated.app_title
import de.github.dudrie.hamster.ui.model.UIViewModel
import kotlinx.coroutines.sync.Semaphore

class SpielFenster(
    private val spiel: SpielViewModel,
    private val importJob: suspend () -> Unit
) :
    AppFenster(Res.string.app_title) {

    /**
     * Nach dem [importieren][importJob] wird gewartet, bis ein Permit dieses [Semaphore] verfügbar ist.
     *
     * Dieser [Semaphore] ist ein (grober) Indikator dafür, wann das tatsächliche Fenster angezeigt wird.
     */
    private val fensterLatch = Semaphore(1, 1)

    suspend fun starte() {
        zeige(fensterLatch)
        importJob()
        fensterLatch.acquire()
        fensterLatch.release()
    }

    @Composable
    override fun FrameWindowScope.inhalt() {
        val viewModel = viewModel { UIViewModel(spiel) }
        val ladtSpiel by spiel.ladtSpiel.collectAsState()

        if (ladtSpiel) {
            LoadingUI()
        } else {
            WindowContent(viewModel)
        }
    }
}
