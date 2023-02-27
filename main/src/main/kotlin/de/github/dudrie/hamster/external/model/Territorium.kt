package de.github.dudrie.hamster.external.model

import de.github.dudrie.hamster.datatypes.SpielOrt
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.interfaces.AbstraktesTerritorium
import de.github.dudrie.hamster.internal.model.territory.GameTerritory
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * Territorium, welches im [HamsterSpiel] benutzt wird.
 *
 * @param spiel [HamsterSpiel], welches das Territorium benutzt [Territorium].
 * @param internalTerritory Referenz auf das interne [GameTerritory].
 */
class Territorium(val spiel: HamsterSpiel, internal val internalTerritory: GameTerritory) : AbstraktesTerritorium() {

    /**
     * [Size] des Territoriums.
     */
    override val abmessungen: Size = internalTerritory.size

    /**
     * Meter, denen ein einzelnes Feld in diesem Territorium entspricht.
     */
    override val feldZuMeterSkalierung: Double
        get() = internalTerritory.tileToMeterScaling

    /**
     * Gibt das [GameTile] am übergebenen [ort] zurück.
     *
     * Der [ort] muss sich innerhalb dieses Territoriums befinden.
     *
     * @return [GameTile] am gegebenen [ort].
     *
     * @throws IllegalArgumentException Wenn der [ort] außerhalb des Territoriums liegt.
     */
    override fun holeFeldBei(ort: SpielOrt): GameTile = internalTerritory.getTileAt(ort)

    /**
     * Gibt die Anzahl Körner am übergebenen [ort] zurück.
     *
     * Der [ort] muss sich innerhalb dieses Territoriums befinden.
     *
     * @return Anzahl Körner am gegebenen [ort].
     *
     * @throws IllegalArgumentException Wenn der [ort] außerhalb des Territoriums liegt.
     */
    fun holeKornAnzahlBei(ort: SpielOrt): Int = internalTerritory.getTileAt(ort).grainCount
    
}
