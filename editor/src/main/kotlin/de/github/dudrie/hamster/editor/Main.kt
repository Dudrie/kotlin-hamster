package de.github.dudrie.hamster.editor

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import de.github.dudrie.hamster.editor.components.EditorContent
import de.github.dudrie.hamster.editor.components.appbar.EditorAppBar
import de.github.dudrie.hamster.editor.components.dialog.DialogPlacer
import de.github.dudrie.hamster.editor.generated.Res
import de.github.dudrie.hamster.editor.generated.editor_app_title
import de.github.dudrie.hamster.ui.theme.ThemeWrapper
import org.jetbrains.compose.resources.stringResource

fun main() {
    application {
        Window(
            title = stringResource(Res.string.editor_app_title),
            onCloseRequest = ::exitApplication,
            state = WindowState(size = DpSize(1000.dp, 750.dp))
        ) {
            ThemeWrapper {
                Scaffold(topBar = { EditorAppBar() }) { innerPadding ->
                    EditorContent(Modifier.padding(innerPadding))
                }
            }

            DialogPlacer()
        }
    }
}

