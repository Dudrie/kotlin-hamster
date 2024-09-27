package de.github.dudrie.hamster.core.model.util

enum class Richtung(val bewegung: Bewegung) {
    Norden(Bewegung(0, -1)),

    Osten(Bewegung(1, 0)),

    Sueden(Bewegung(0, 1)),

    Westen(Bewegung(-1, 0));

    fun nachLinksGedreht(): Richtung = when (this) {
        Norden -> Westen
        Osten -> Sueden
        Sueden -> Osten
        Westen -> Norden
    }
}
