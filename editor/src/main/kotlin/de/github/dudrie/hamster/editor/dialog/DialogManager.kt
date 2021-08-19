package de.github.dudrie.hamster.editor.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

@Composable
fun DialogManager() {
    val dialog by DialogService.dialog
    dialog?.let { it() }
}
