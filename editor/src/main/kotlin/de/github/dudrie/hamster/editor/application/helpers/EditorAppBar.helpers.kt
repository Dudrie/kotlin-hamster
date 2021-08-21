package de.github.dudrie.hamster.editor.application.helpers

import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import de.github.dudrie.hamster.editor.application.EditorState
import de.github.dudrie.hamster.editor.dialog.ConfirmDialogResult
import de.github.dudrie.hamster.editor.dialog.DialogService
import de.github.dudrie.hamster.i18n.HamsterString

/**
 * Helper function used in the [EditorAppBar][de.github.dudrie.hamster.editor.application.EditorAppBar] to create a new territory.
 *
 * This asks the user for confirmation before creating a new territory.
 */
internal suspend fun handleCreateNewTerritory() {
    val result = DialogService.askForConfirmation(
        text = { Text(HamsterString.get("editor.dialog.new.territory.text")) },
        title = { Text(HamsterString.get("editor.dialog.new.territory.title")) },
        confirm = { Text(HamsterString.get("editor.dialog.new.territory.confirm")) },
        dismiss = { Text(HamsterString.get("button.cancel")) },
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
        text = { Text(HamsterString.get("editor.dialog.confirm.open.territory.text")) },
        title = { Text(HamsterString.get("editor.dialog.confirm.open.territory.title")) },
        confirm = { Text(HamsterString.get("editor.dialog.confirm.open.territory.button.confirm")) },
        dismiss = { Text(HamsterString.get("button.cancel")) })

    if (confirm == ConfirmDialogResult.Dismiss) {
        return
    }

    DialogService.askForFileToLoad()?.let { path ->
        val result = EditorState.loadFromFile(path)

        if (result.isSuccess) {
            snackbarHost.showSnackbar(
                HamsterString.get("editor.snackbar.file.loaded.success"),
                HamsterString.get("snackbar.close")
            )
        } else {
            snackbarHost.showSnackbar(
                HamsterString.getWithFormat(
                    "snackbar.error",
                    result.error?.localizedMessage ?: HamsterString.get("error.unknown")
                ),
                HamsterString.get("snackbar.close")
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
                HamsterString.get("editor.snackbar.file.saved.success"),
                HamsterString.get("snackbar.close")
            )
        } else {
            snackbarHost.showSnackbar(
                HamsterString.getWithFormat(
                    "snackbar.error",
                    result.error?.localizedMessage ?: HamsterString.get("error.unknown")
                ),
                HamsterString.get("snackbar.close")
            )
        }
    }
}
