package de.github.dudrie.hamster.editor.components.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.AwtWindow
import androidx.compose.ui.window.FrameWindowScope
import java.awt.FileDialog
import java.io.File
import java.nio.file.Path

@Composable
fun FrameWindowScope.FileDialog(
    title: String,
    isLoad: Boolean,
    onSelectFile: (path: Path?) -> Unit
) {
    AwtWindow(
        create = {
            object : FileDialog(window, title, if (isLoad) LOAD else SAVE) {
                override fun setVisible(visible: Boolean) {
                    super.setVisible(visible)
                    if (visible) {
                        if (file != null) {
                            onSelectFile(File(directory).resolve(file).toPath())
                        } else {
                            onSelectFile(null)
                        }
                    }
                }
            }
        },
        dispose = FileDialog::dispose
    )
}
