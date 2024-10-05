package de.github.dudrie.hamster.ui.extensions

import de.github.dudrie.hamster.core.model.util.Richtung

internal fun Richtung.getGrad(): Float = when (this) {
    Richtung.Norden -> 270f
    Richtung.Osten -> 0f
    Richtung.Sueden -> 90f
    Richtung.Westen -> 180f
}
