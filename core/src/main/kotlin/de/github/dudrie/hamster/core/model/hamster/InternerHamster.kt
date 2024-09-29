package de.github.dudrie.hamster.core.model.hamster

import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.core.model.util.Richtung


data class InternerHamster(
    val position: Position,
    val richtung: Richtung,
    val inventar: List<InventarInhalt>,
    val anzahlSchritte: Int = 0
) {

    val kornAnzahl: Int
        get() = inventar.filterIsInstance<Korn>().size


    fun laufe(): InternerHamster = copy(
        position = position + richtung.bewegung,
        anzahlSchritte = anzahlSchritte + 1
    )

    fun dreheDichNach(richtung: Richtung): InternerHamster = copy(
        richtung = richtung
    )

    fun fugeZuInventarHinzu(inhalt: InventarInhalt): InternerHamster = copy(
        inventar = inventar + inhalt
    )

    internal inline fun <reified I : InventarInhalt> entferneEinsAusInventar(): InternerHamster {
        val gegenstand = inventar.find { it is I }
            ?: throw IllegalArgumentException("ERR_NO_MATCHING_ITEM_IN_INVENTORY")

        return entferneAusInventar(gegenstand)
    }

    private fun entferneAusInventar(inhalt: InventarInhalt): InternerHamster = copy(
        inventar = inventar - inhalt
    )

    fun getPositionNachSchritt(): Position = position + richtung.bewegung

}
