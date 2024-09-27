package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.Kachel
import de.github.dudrie.hamster.core.model.kachel.KornInhalt
import de.github.dudrie.hamster.core.model.kachel.Leer
import de.github.dudrie.hamster.core.model.kachel.Wand
import de.github.dudrie.hamster.ui.extensions.getGrad
import de.github.dudrie.hamster.ui.generated.Res
import de.github.dudrie.hamster.ui.generated.hamster
import de.github.dudrie.hamster.ui.generated.wall
import de.github.dudrie.hamster.ui.model.UIViewModel
import org.jetbrains.compose.resources.imageResource

@Composable
fun GameTileContent(tile: Kachel, hamster: InternerHamster?, viewModel: UIViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (tile.inhalt) {
            is KornInhalt -> Text("K")

            is Wand -> Image(
                imageResource(Res.drawable.wall),
                null,
                modifier = Modifier.fillMaxSize()
            )

            Leer -> {}
        }

        hamster?.let {
            Box(Modifier.alpha(if (uiState.hideHamster) 0.35f else 1f)) {
                Image(
                    imageResource(Res.drawable.hamster),
                    null,
                    modifier = Modifier.fillMaxSize().rotate(it.richtung.getGrad())
                )
            }
        }
    }
}
