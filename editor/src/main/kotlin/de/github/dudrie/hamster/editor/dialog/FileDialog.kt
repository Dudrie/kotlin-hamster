package de.github.dudrie.hamster.editor.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.AwtWindow
import androidx.compose.ui.window.FrameWindowScope
import java.awt.FileDialog
import java.io.File
import java.nio.file.Path

/**
 * A dialog which shows the native file dialog of the system.
 *
 * Can be used to open or save single files.
 *
 * @param title Title shown in the dialog window.
 * @param isOpenFile If `true` a dialog will be shown which **opens** a file, else the dialog will be a **save** dialog.
 * @param onFileSelection Gets called if the user selects a file or closes the dialog. The selected file will be passed as the argument. This will be `null` if the user did not select any file (ie closing the dialog).
 */
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
