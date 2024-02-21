package de.github.dudrie.hamster.interfaces

import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.datatypes.SpielOrt
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * Abstraktion für ein Territorium, welches während eines Spiels (oder im Editor) genutzt wird.
 */
abstract class AbstraktesTerritorium {
    /**
     * [Size] dieses Territoriums.
     */
    abstract val abmessungen: Size

    /**
     * Skalierung, wie vielen Metern ein Feld entspricht.
     */
    abstract val feldZuMeterSkalierung: Double

    /**
     * Gibt das [GameTile] bei [ort] zurück.
     *
     * Der [ort] muss in diesem Territorium liegen.
     */
    abstract fun holeFeldBei(ort: SpielOrt): GameTile

    /**
     * Kann das [GameTile] bei [ort] betreten werden?
     *
     * Der [ort] wird als "frei" angesehen, wenn er innerhalb des Territoriums liegt und nicht blockiert ist.
     */
    fun istFrei(ort: SpielOrt): Boolean =
        abmessungen.isLocationInside(ort) && !holeFeldBei(ort).blocked

    /**
     * Ist das [GameTile] bei [ort] blockiert, kann also nicht betreten werden?
     *
     * Der [ort] muss innerhalb des Territoriums liegen.
     */
    fun istBlockiert(ort: SpielOrt): Boolean = holeFeldBei(ort).blocked

    /**
     * Gibt die Anzahl der Körner auf dem [GameTile] bei [ort] zurück.
     *
     * Der [ort] muss innerhalb des Territoriums liegen.
     */
    fun holeAnzahlKoernerBei(ort: SpielOrt): Int = holeFeldBei(ort).grainCount


}
