package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

/**
 * Special button that has an [icon] and a [text] below.
 *
 * @param onClick Gets called if the user clicks the button.
 * @param enabled Is this button enabled? Defaults to `true`.
 * @param icon Composable shown in the icon slot.
 * @param text Text shown below the icon.
 * @param modifier Modifier applied to the underlying [Surface].
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IconButtonWithText(
    onClick: () -> Unit,
    enabled: Boolean = true,
    icon: @Composable ColumnScope.() -> Unit,
    text: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        enabled = enabled,
        role = Role.Button,
        shape = RoundedCornerShape(4.dp),
        modifier = modifier.wrapContentSize()
    ) {
        Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            icon()

            val currentColor = LocalContentColor.current
            val contentColor = remember(enabled) { if (!enabled) Color.Gray else currentColor }
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                text()
            }
        }
    }
}
