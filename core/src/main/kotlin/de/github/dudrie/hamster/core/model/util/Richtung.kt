package de.github.dudrie.hamster.core.model.util

/**
 * Beschreibt eine Richtung im Territorium.
 *
 * Sie orientieren sich dabei an den vier Himmelsrichtungen.
 *
 * @param bewegung Die [Bewegung], wenn man sich **genau ein** Feld in diese [Richtung] bewegen würde.
 */
enum class Richtung(val bewegung: Bewegung) {
    /**
     * In Richtung der oberen Kante des Territoriums.
     */
    Norden(Bewegung(0, -1)),

    /**
     * In Richtung der rechten Kante des Territoriums.
     */
    Osten(Bewegung(1, 0)),

    /**
     * In Richtung der unteren Kante des Territoriums.
     */
    Sueden(Bewegung(0, 1)),

    /**
     * In Richtung der linken Kante des Territoriums.
     */
    Westen(Bewegung(-1, 0));

    /**
     * Gibt die um 90 Grad gegen den Uhrzeigersinn gedrehte [Richtung] zurück.
     *
     * @return [Richtung] um 90 Grad gegen den UZS gedreht.
     */
    fun nachLinksGedreht(): Richtung = when (this) {
        Norden -> Westen
        Osten -> Norden
        Sueden -> Osten
        Westen -> Sueden
    }
}
