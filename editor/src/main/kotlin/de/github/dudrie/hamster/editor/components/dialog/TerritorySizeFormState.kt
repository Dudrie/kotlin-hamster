package de.github.dudrie.hamster.editor.components.dialog

import de.github.dudrie.commons.forms.model.FormVariableValidationResult
import de.github.dudrie.commons.forms.model.IntFormValue
import de.github.dudrie.hamster.core.model.territory.Abmessungen
import de.github.dudrie.hamster.editor.generated.Res
import de.github.dudrie.hamster.editor.generated.territory_size_dialog_invalid_number
import de.github.dudrie.hamster.editor.generated.territory_size_dialog_not_positiv
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.getString

class TerritorySizeFormState(initialSize: Abmessungen) {
    val width =
        IntFormValue(
            initialValue = initialSize.breite,
            errorInvalidText = runBlocking { getString(Res.string.territory_size_dialog_invalid_number) }
        ) {
            if (it > 0) {
                FormVariableValidationResult.Valid
            } else {
                FormVariableValidationResult.Error(runBlocking {
                    getString(
                        Res.string.territory_size_dialog_not_positiv,
                        "Breite"
                    )
                })
            }
        }

    val height =
        IntFormValue(
            initialValue = initialSize.hohe,
            errorInvalidText = runBlocking { getString(Res.string.territory_size_dialog_invalid_number) }
        ) {
            if (it > 0) {
                FormVariableValidationResult.Valid
            } else {
                FormVariableValidationResult.Error(runBlocking {
                    getString(
                        Res.string.territory_size_dialog_not_positiv,
                        "Höhe"
                    )
                })
            }
        }

    val isValid: Boolean
        get() = width.isValid && height.isValid
}
