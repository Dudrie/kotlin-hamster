package de.github.dudrie.hamster.editor.components.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FrameWindowScope.DialogPlacer(state: DialogState = viewModel()) {
    state.dialog?.let { it() }
}
