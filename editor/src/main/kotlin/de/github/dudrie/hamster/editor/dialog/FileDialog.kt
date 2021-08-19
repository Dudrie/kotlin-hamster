package de.github.dudrie.hamster.editor.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.AwtWindow
import androidx.compose.ui.window.FrameWindowScope
import java.awt.FileDialog
import java.io.File
import java.nio.file.Path

@Composable
fun FrameWindowScope.FileDialog(title: String, isOpenFile: Boolean, onFileSelection: (path: Path?) -> Unit) {
    AwtWindow(
        create = {
            object : FileDialog(window, "CHOOSE FILE", if (isOpenFile) LOAD else SAVE) {
                override fun setVisible(visible: Boolean) {
                    super.setVisible(visible)
                    if (visible) {
                        if (file != null) {
                            onFileSelection(File(directory).resolve(file).toPath())
                        } else {
                            onFileSelection(null)
                        }
                    }
                }
            }.apply { this.title = title }
        },
        dispose = FileDialog::dispose
    )
}
