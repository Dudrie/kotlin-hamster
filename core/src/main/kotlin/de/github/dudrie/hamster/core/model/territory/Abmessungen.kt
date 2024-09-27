package de.github.dudrie.hamster.core.model.territory

import de.github.dudrie.hamster.core.model.util.Position

data class Abmessungen(val breite: Int, val hohe: Int) {

    fun istInnerhalb(position: Position): Boolean =
        position.x in 0..breite && position.y in 0..hohe

    fun getAlleInnerhalb(): Iterator<Position> = object : Iterator<Position> {
        private var aktuelleZeile = 0
        private var aktuelleSpalte = 0

        override fun hasNext(): Boolean = aktuelleZeile < hohe

        override fun next(): Position {
            val position = Position(x = aktuelleSpalte, y = aktuelleZeile)
            aktuelleSpalte += 1

            if (aktuelleSpalte >= breite) {
                aktuelleSpalte = 0
                aktuelleZeile += 1
            }

            return position
        }
    }

}
