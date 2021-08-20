package de.github.dudrie.hamster.editor.application.helpers

import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import de.github.dudrie.hamster.ResString
import de.github.dudrie.hamster.editor.application.EditorState
import de.github.dudrie.hamster.editor.dialog.ConfirmDialogResult
import de.github.dudrie.hamster.editor.dialog.DialogService

/**
 * Helper function used in the [EditorAppBar][de.github.dudrie.hamster.editor.application.EditorAppBar] to create a new territory.
 *
 * This asks the user for confirmation before creating a new territory.
 */
internal suspend fun handleCreateNewTerritory() {
    val result = DialogService.askForConfirmation(
        text = { Text(ResString.get("editor.dialog.new.territory.text")) },
        title = { Text(ResString.get("editor.dialog.new.territory.title")) },
        confirm = { Text(ResString.get("editor.dialog.new.territory.confirm")) },
        dismiss = { Text(ResString.get("button.cancel")) },
    )
    if (result == ConfirmDialogResult.Confirm) {
        EditorState.resetTerritory()
    }
}

/**
 * Helper function used in the [EditorAppBar][de.github.dudrie.hamster.editor.application.EditorAppBar] to load a territory from the disk.
 *
 * This asks the user for confirmation before loading a territory.
 */
internal suspend fun handleOpenTerritory(snackbarHost: SnackbarHostState) {
    val confirm = DialogService.askForConfirmation(
        text = { Text(ResString.get("editor.dialog.confirm.open.territory.text")) },
        title = { Text(ResString.get("editor.dialog.confirm.open.territory.title")) },
        confirm = { Text(ResString.get("editor.dialog.confirm.open.territory.button.confirm")) },
        dismiss = { Text(ResString.get("button.cancel")) })

    if (confirm == ConfirmDialogResult.Dismiss) {
        return
    }

    DialogService.askForFileToLoad()?.let { path ->
        val result = EditorState.loadFromFile(path)

        if (result.isSuccess) {
            snackbarHost.showSnackbar(
                ResString.get("editor.snackbar.file.loaded.success"),
                ResString.get("snackbar.close")
            )
        } else {
            snackbarHost.showSnackbar(
                ResString.getWithFormat(
                    "snackbar.error",
                    result.error?.localizedMessage ?: ResString.get("error.unknown")
                ),
                ResString.get("snackbar.close")
            )
        }
    }
}

/**
 * Helper function used in the [EditorAppBar][de.github.dudrie.hamster.editor.application.EditorAppBar] to save the current territory.
 */
internal suspend fun handleSaveTerritory(snackbarHost: SnackbarHostState) {
    DialogService.askForFileToSave()?.let { path ->
        val result = EditorState.saveToFile(path)
        if (result.isSuccess) {
            snackbarHost.showSnackbar(
                ResString.get("editor.snackbar.file.saved.success"),
                ResString.get("snackbar.close")
            )
        } else {
            snackbarHost.showSnackbar(
                ResString.getWithFormat(
                    "snackbar.error",
                    result.error?.localizedMessage ?: ResString.get("error.unknown")
                ),
                ResString.get("snackbar.close")
            )
        }
    }
}
