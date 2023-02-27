package de.github.dudrie.hamster.datatypes

/**
 * Repräsentiert eine Richtung im Simulator.
 *
 * Richtungen im Spiel benutzen die Himmelsrichtungen und werden hauptsächlich dazu benutzt, die Blickrichtung des [GameHamster][de.github.dudrie.hamster.internal.model.hamster.GameHamster] zu beschreiben.
 *
 * @param directionVector [LocationVector] die dazugehörige Verschiebung beim Bewegen.
 */
enum class Richtung(val directionVector: LocationVector) {
    /**
     * In Richtung der oberen Kante des Spielfeldes.
     */
    Norden(LocationVector(0, -1)),

    /**
     * In Richtung der rechten Kante des Spielfeldes.
     */
    Osten(LocationVector(1, 0)),

    /**
     * In Richtung der unteren Kante des Spielfeldes.
     */
    Sueden(LocationVector(0, 1)),

    /**
     * In Richtung der linken Kante des Spielfeldes.
     */
    Westen(LocationVector(-1, 0));

    /**
     * Gibt die um 90 Grad gegen den Uhrzeigersinn gedrehte [Richtung] zurück.
     *
     * @return [Richtung] um 90 Grad gegen den UZS gedreht.
     */
    fun nachLinksGedreht(): Richtung {
        return when (this) {
            Norden -> Westen
            Osten -> Norden
            Sueden -> Osten
            Westen -> Sueden
        }
    }
}
