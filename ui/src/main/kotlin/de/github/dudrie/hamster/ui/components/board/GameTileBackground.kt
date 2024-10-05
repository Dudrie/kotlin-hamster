package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.github.dudrie.hamster.ui.generated.Res
import de.github.dudrie.hamster.ui.generated.floor
import org.jetbrains.compose.resources.imageResource

@Composable
fun GameTileBackground() {
    Image(imageResource(Res.drawable.floor), null, modifier = Modifier.fillMaxSize())
}
