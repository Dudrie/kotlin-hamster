package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A select component with a dropdown menu.
 *
 * @param items All items present in the dropdown menu.
 * @param itemToString Returns a string representation of the item which gets displayed to the user. The returned string should be different for all items.
 * @param value The currently selected value.
 * @param onValueChanged Gets called with the new value if the value gets changed.
 * @param modifier Modifier that gets applied to the underlying [Box].
 *
 * @param T Type of single item.
 */
@Composable
fun <T> Select(
    items: List<T>,
    itemToString: (T) -> String,
    value: T,
    onValueChanged: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    var dropDownExpanded by remember { mutableStateOf(false) }

    Box(modifier.wrapContentSize(Alignment.TopStart)) {
        OutlinedButton(onClick = { dropDownExpanded = true }, modifier = modifier) {
            Text(itemToString(value), modifier = Modifier.padding(end = 4.dp).align(Alignment.CenterVertically))
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        DropdownMenu(expanded = dropDownExpanded, onDismissRequest = { dropDownExpanded = false }) {
            items.forEach { item ->
                DropdownMenuItem({
                    onValueChanged(item)
                    dropDownExpanded = false
                }) { Text(itemToString(item)) }
            }
        }
    }
}

