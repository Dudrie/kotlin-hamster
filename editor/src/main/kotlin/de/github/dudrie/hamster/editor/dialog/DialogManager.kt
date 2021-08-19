package de.github.dudrie.hamster.editor.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.FrameWindowScope

@Composable
fun FrameWindowScope.DialogManager() {
    val dialog by DialogService.dialog
    dialog?.let { it() }
}
