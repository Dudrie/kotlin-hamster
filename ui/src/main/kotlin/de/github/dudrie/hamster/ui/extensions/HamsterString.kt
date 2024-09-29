package de.github.dudrie.hamster.ui.extensions

import androidx.compose.runtime.Composable
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.HamsterStringId
import de.github.dudrie.hamster.ui.generated.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HamsterString.getText(): String {
    val resource = convertToStringResource()

    return if (args.isNotEmpty()) {
        stringResource(resource, *args)
    } else {
        stringResource(resource)
    }
}

internal fun HamsterString.convertToStringResource(): StringResource = when (id) {
    HamsterStringId.KOMMANDO_HAMSTER_DREHE_LINKS -> Res.string.kommando_hamster_drehe_links
    HamsterStringId.KOMMANDO_HAMSTER_LAUFE -> Res.string.kommando_hamster_laufe
    HamsterStringId.KOMMANDO_HAMSTER_LEGE_KORN_AB -> Res.string.kommando_hamster_lege_korn_ab
    HamsterStringId.KOMMANDO_HAMSTER_SAGE -> Res.string.kommando_hamster_sage
    HamsterStringId.KOMMANDO_HAMSTER_SAMMLE_KORN_AUF -> Res.string.kommando_hamster_sammle_korn_auf
    HamsterStringId.KOMMANDO_TERRITORIUM_SPAWNE_HAMSTER -> Res.string.kommando_territorium_spawn_hamster
    HamsterStringId.ERR_NO_GRAIN_IN_MOUTH -> TODO()
    HamsterStringId.ERR_NO_GRAIN_ON_TILE -> TODO()
    HamsterStringId.ERR_POSITION_NOT_IN_TERRITORY -> TODO()
    HamsterStringId.ERR_TILE_BLOCKED -> TODO()
}
