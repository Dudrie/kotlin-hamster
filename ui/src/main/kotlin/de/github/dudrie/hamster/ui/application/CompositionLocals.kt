package de.github.dudrie.hamster.ui.application

import androidx.compose.runtime.compositionLocalOf
import de.github.dudrie.hamster.interfaces.IHamsterGame

internal val HamsterGameLocal = compositionLocalOf<IHamsterGame> { error("No hamster game was provided.") }
