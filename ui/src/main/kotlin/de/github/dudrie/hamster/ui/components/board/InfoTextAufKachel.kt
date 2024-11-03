package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ui.theme.SpielTypographyLocal

@Composable
fun BoxScope.InfoTextAufKachel(text: String, alignment: Alignment = Alignment.BottomEnd) {
    Surface(
        contentColor = Color.White,
        color = Color.Gray.copy(alpha = 0.8f),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(2.dp).size(36.dp).align(alignment)
    ) {
        Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
            Text(
                text = text,
                style = SpielTypographyLocal.current.kornAnzahl,
                textAlign = TextAlign.Center
            )
        }
    }
}
