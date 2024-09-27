package de.github.dudrie.hamster.core.model.util

data class Position(val x: Int, val y: Int) {

    operator fun plus(bewegung: Bewegung): Position = copy(
        x = x + bewegung.deltaX,
        y = y + bewegung.deltaY
    )

}
