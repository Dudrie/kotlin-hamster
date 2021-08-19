package de.github.dudrie.hamster.ui.components.appbar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ui.components.ControlButtonColors

@Composable
fun AppBarButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable() (RowScope.() -> Unit)
) {
    OutlinedButton(
        onClick = onClick,
        colors = ControlButtonColors,
        border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
        enabled = enabled,
        modifier = modifier,
        contentPadding = contentPadding,
        content = content
    )
}
