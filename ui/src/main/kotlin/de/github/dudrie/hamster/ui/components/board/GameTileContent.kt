package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
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
import org.jetbrains.compose.resources.imageResource

@Composable
fun GameTileContent(
    tile: Kachel,
    hamster: InternerHamster?,
    hideHamster: Boolean,
    mehrAlsEinHamster: Boolean
) {
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

                infoText?.let { InfoTextAufKachel(it) }
            }

            is Wand -> Image(
                imageResource(Res.drawable.wall),
                null,
                modifier = Modifier.fillMaxSize()
            )

            Leer -> {}
        }

        hamster?.let {
            Box(Modifier.alpha(if (hideHamster) 0.35f else 1f)) {
                Image(
                    imageResource(Res.drawable.hamster),
                    null,
                    modifier = Modifier.fillMaxSize().padding(8.dp).rotate(it.richtung.getGrad())
                )

                if (mehrAlsEinHamster) {
                    InfoTextAufKachel("${it.nummer}", Alignment.BottomStart)
                }
            }
        }
    }
}
