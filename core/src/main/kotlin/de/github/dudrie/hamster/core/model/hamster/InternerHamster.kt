package de.github.dudrie.hamster.core.model.hamster

import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.core.model.util.Richtung

/**
 * Stellt einen Hamster im Hamsterspiel dar.
 *
 * @param position Die [Position] des Hamsters.
 * @param richtung Die [Richtung], in die der Hamster schaut.
 * @param inventar Das Inventar des Hamsters. Enthält alle [InventarInhalt]e, die der Hamster aktuell bei sich trägt.
 * @param anzahlSchritte Die Anzahl an Schritten, die der Hamster bisher gemacht hat.
 */
data class InternerHamster(
    val position: Position,
    val richtung: Richtung,
    val inventar: List<InventarInhalt>,
    val anzahlSchritte: Int = 0
) {

    /**
     * Die Anzahl an Körnern, die der Hamster im Mund / Inventar hat.
     */
    val kornAnzahl: Int
        get() = inventar.filterIsInstance<Korn>().size

    /**
     * Lässt den Hamster einen Schritt in seine [Blickrichtung][richtung] laufen.
     */
    fun laufe(): InternerHamster = copy(
        position = position + richtung.bewegung,
        anzahlSchritte = anzahlSchritte + 1
    )

    /**
     * Dreht den Hamster, sodass er anschließend in die übergebene [richtung] schaut.
     */
    fun dreheDichNach(richtung: Richtung): InternerHamster = copy(
        richtung = richtung
    )

    /**
     * Fügt den gegebenen [inhalt] zum [inventar] des Hamsters hinzu.
     */
    fun fugeZuInventarHinzu(inhalt: InventarInhalt): InternerHamster = copy(
        inventar = inventar + inhalt
    )

    /**
     * Entfernt einen Gegenstand vom gegebenen Typ [I] aus dem Inventar des Hamsters.
     *
     * @throws IllegalArgumentException Wenn der Hamster keinen Gegenstand vom Typ [I] im Inventar hat.
     */
    internal inline fun <reified I : InventarInhalt> entferneEinsAusInventar(): InternerHamster {
        val gegenstand = inventar.find { it is I }
            ?: throw IllegalArgumentException("ERR_NO_MATCHING_ITEM_IN_INVENTORY")

        return entferneAusInventar(gegenstand)
    }

    /**
     * Gibt die [Position] des Hamsters zurück, die er nach einem Schritt in seine [richtung] hätte.
     */
    fun getPositionNachSchritt(): Position = position + richtung.bewegung

    /**
     * Entfernt den [inhalt] aus dem [inventar].
     */
    private fun entferneAusInventar(inhalt: InventarInhalt): InternerHamster = copy(
        inventar = inventar - inhalt
    )
}
