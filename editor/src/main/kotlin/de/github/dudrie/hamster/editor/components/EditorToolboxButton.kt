package de.github.dudrie.hamster.editor.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

/**
 * Button used in the toolbox.
 *
 * @param onClick Called if the user clicks on the button.
 * @param icon Icon to show on the left of the button.
 * @param text Text to show to the right of the [icon].
 * @param color Background color of the [Surface].
 * @param border Border around the [Surface].
 * @param modifier [Modifier] applied to the underlying [Surface].
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditorToolboxButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    color: Color = MaterialTheme.colors.surface,
    border: BorderStroke? = ButtonDefaults.outlinedBorder,
    modifier: Modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
) {
    Surface(
        onClick = onClick,
        role = Role.Button,
        color = color,
        contentColor = MaterialTheme.colors.contentColorFor(color),
        shape = MaterialTheme.shapes.small,
        border = border,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.defaultMinSize(minWidth = ButtonDefaults.MinWidth, minHeight = 48.dp).padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon()

            Box(Modifier.padding(start = 8.dp)) {
                text()
            }
        }
    }
}
