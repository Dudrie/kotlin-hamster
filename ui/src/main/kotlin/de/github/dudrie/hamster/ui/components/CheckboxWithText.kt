package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * [Checkbox] with an additional [text]. The [position] of the [Checkbox] can be configured.
 *
 * @param text Text next to the [Checkbox].
 * @param position [CheckboxPosition] of the checkbox.
 *
 * @see Checkbox
 */
@Composable
fun CheckboxWithText(
    text: String,
    position: CheckboxPosition = CheckboxPosition.LEFT,
    checked: Boolean,
    onCheckedChange: (checked: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: CheckboxColors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
) {
    val rowModifier = remember(enabled, onCheckedChange) {
        if (enabled) modifier.clickable { onCheckedChange(!checked) } else modifier
    }
    val textModifier = remember(position) {
        Modifier.padding(
            start = if (position == CheckboxPosition.LEFT) 0.dp else 8.dp,
            end = if (position == CheckboxPosition.RIGHT) 8.dp else 0.dp
        )
    }
    val textColor =
        if (enabled) MaterialTheme.colors.onSurface else MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)

    Row(rowModifier) {
        if (position == CheckboxPosition.LEFT) {
            Checkbox(
                checked = checked,
                onCheckedChange = { onCheckedChange(it) },
                enabled = enabled,
                interactionSource = interactionSource,
                colors = colors
            )
        }

        Text(
            text = text,
            modifier = textModifier,
            color = textColor
        )

        if (position == CheckboxPosition.RIGHT) {
            Checkbox(
                checked = checked,
                onCheckedChange = { onCheckedChange(it) },
                enabled = enabled,
                interactionSource = interactionSource,
                colors = colors
            )
        }
    }
}

/**
 * Position of the [Checkbox] of the [CheckboxWithText] component.
 */
enum class CheckboxPosition {
    /**
     * The checkbox will be shown to the left of the text.
     */
    LEFT,

    /**
     * The checkbox will be shown to the right of the text.
     */
    RIGHT
}
