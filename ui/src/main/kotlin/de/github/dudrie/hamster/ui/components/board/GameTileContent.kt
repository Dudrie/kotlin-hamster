package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.Kachel
import de.github.dudrie.hamster.core.model.kachel.KornInhalt
import de.github.dudrie.hamster.core.model.kachel.Leer
import de.github.dudrie.hamster.core.model.kachel.Wand
import de.github.dudrie.hamster.ui.extensions.getGrad
import de.github.dudrie.hamster.ui.generated.Res
import de.github.dudrie.hamster.ui.generated.grain
import de.github.dudrie.hamster.ui.generated.hamster
import de.github.dudrie.hamster.ui.generated.wall
import de.github.dudrie.hamster.ui.model.UIViewModel
import de.github.dudrie.hamster.ui.theme.SpielTypographyLocal
import org.jetbrains.compose.resources.imageResource

@Composable
fun GameTileContent(tile: Kachel, hamster: InternerHamster?, viewModel: UIViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val inhalt = tile.inhalt) {
            is KornInhalt -> {
                val infoText: String? = when {
                    inhalt.verbergeKornAnzahl -> "?"
                    inhalt.anzahl == 1 -> null
                    else -> "${inhalt.anzahl}"
                }

                Image(
                    imageResource(Res.drawable.grain),
                    null,
                    modifier = Modifier.fillMaxSize()
                )

                infoText?.let {
                    Surface(
                        contentColor = Color.White,
                        color = Color.Gray.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.padding(2.dp).size(36.dp).align(Alignment.BottomEnd)
                    ) {
                        Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                            Text(
                                text = it,
                                style = SpielTypographyLocal.current.kornAnzahl,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

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
                    modifier = Modifier.fillMaxSize().padding(8.dp).rotate(it.richtung.getGrad())
                )
            }
        }
    }
}
