package de.github.dudrie.hamster.core.extensions

import de.github.dudrie.hamster.core.model.kachel.Kachel
import de.github.dudrie.hamster.core.model.territory.Abmessungen
import de.github.dudrie.hamster.core.model.util.Position

/**
 * Berechnet die [Abmessungen] auf Basis der gespeicherten [Kachel]n und ihrer [Position]en.
 */
fun Map<Position, Kachel>.getAbmessungen(): Abmessungen = Abmessungen(
    breite = keys.maxOf { it.x } + 1,
    hohe = keys.maxOf { it.y } + 1
)
