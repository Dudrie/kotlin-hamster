package de.github.dudrie.hamster.core.model.territory

import de.github.dudrie.hamster.core.extensions.getAbmessungen
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.Kachel
import de.github.dudrie.hamster.core.model.kachel.Kachelinhalt
import de.github.dudrie.hamster.core.model.util.Position

/**
 * Ein Territorium des Hamsterspiels.
 *
 * @param hamster Eine [List] aller [InternerHamster], die in diesem Territorium leben.
 * @param kacheln Alle [Kachel]n, aus denen dieses Territorium besteht.
 * @param kachelZuMeterSkalierung Wie vielen Metern entspricht eine [Kachel] dieses Territoriums?
 */
data class InternesTerritorium(
    val hamster: List<InternerHamster>,
    val kacheln: Map<Position, Kachel>,
    val kachelZuMeterSkalierung: Double = 1.0
) {

    /**
     * @see abmessungen
     */
    private var _abmessungen: Abmessungen? = null

    /**
     * [Abmessungen] dieses Territoriums.
     */
    val abmessungen: Abmessungen
        get() =
            if (_abmessungen != null) {
                _abmessungen!!
            } else {
                _abmessungen = kacheln.getAbmessungen()
                _abmessungen!!
            }

    /**
     * Ersetzt den [alterHamster] dieses Tutoriums durch den [neuerHamster].
     */
    fun ersetzeHamster(
        alterHamster: InternerHamster,
        neuerHamster: InternerHamster
    ): InternesTerritorium {
        val index = this.hamster.indexOf(alterHamster)
        require(index != -1) { "ERR_HAMSTER_NOT_IN_TERRITORY" }
        val neueHamster = this.hamster.toMutableList()

        neueHamster[index] = neuerHamster
        return copy(hamster = neueHamster)
    }

    /**
     * Gibt die [Kachel] an der übergebenen [position] zurück.
     *
     * @throws NoSuchElementException Es gibt an der [position] keine Kachel im Territorium.
     */
    fun getKachelBei(position: Position): Kachel {
        return kacheln[position]
            ?: throw NoSuchElementException("ERR_NO_TILE_AT_POSITION")
    }

    /**
     * Hat dieses Territorium mehr als einen Hamster?
     */
    fun hatMehrereHamster(): Boolean = hamster.size > 1

    /**
     * Ersetzt die [Kachel] an der [position] durch die [neueKachel].
     *
     * @throws RuntimeException Es gibt an der [position] keine [Kachel] im Territorium.
     */
    private fun ersetzeKachel(position: Position, neueKachel: Kachel): InternesTerritorium {
        require(kacheln.containsKey(position)) { "ERR_NO_TILE_FOR_POSITION" }
        val neueKacheln = kacheln.toMutableMap()
        neueKacheln[position] = neueKachel
        return copy(kacheln = neueKacheln)
    }

    /**
     * Ersetzt den [Kachelinhalt] der [Kachel] an der [position] durch den [neuerInhalt].
     *
     * @throws RuntimeException Es gibt an der [position] keine [Kachel] im Territorium.
     */
    fun ersetzeKachelInhalt(position: Position, neuerInhalt: Kachelinhalt): InternesTerritorium {
        val kachel = kacheln[position] ?: throw RuntimeException("ERR_NO_TILE_FOR_POSITION")
        return ersetzeKachel(position, kachel.copy(inhalt = neuerInhalt))
    }

    /**
     * Ist die [position] innerhalb dieses Territoriums?
     */
    fun istPositionInnerhalb(position: Position): Boolean = abmessungen.istInnerhalb(position)

    /**
     * Ist die [position] blockiert?
     *
     * _Blockiert bedeutet, dass sich ein Hamster **nicht** auf diese [Position] bewegen kann._
     *
     * @throws RuntimeException Es gibt an der [position] keine [Kachel] im Territorium.
     */
    fun istBlockiert(position: Position): Boolean {
        val kachel = kacheln[position]
        require(kachel != null) { "ERR_NO_TILE_AT_POSITION" }

        return kachel.inhalt.blocktBewegung
    }

    /**
     * Hat die [Kachel] an der [position] einen [Kachelinhalt] vom Typ [I]?
     *
     * @see getKachelBei
     */
    inline fun <reified I : Kachelinhalt> hatInhalt(position: Position): Boolean =
        getKachelBei(position).inhalt is I

    /**
     * Gibt den [Kachelinhalt] vom Typ [I] von der [Kachel] an der [position] zurück.
     *
     * @throws IllegalArgumentException Die [Kachel] an der [position] hat keinen Inhalt vom Typ [I].
     *
     * @see getKachelBei
     */
    inline fun <reified I : Kachelinhalt> getInhalt(position: Position): I =
        getKachelBei(position).inhalt as? I
            ?: throw IllegalArgumentException("ERR_NO_MATCHING_TILE_CONTENT")

    /**
     * Gibt den [Kachelinhalt] vom Typ [I] von der [Kachel] an der [position] zurück.
     *
     * Gibt es an der [position] keinen solchen Inhalt, wird mit [erstelleNeu] ein neuer, entsprechender Inhalt erzeugt.
     *
     * @see getInhalt
     */
    inline fun <reified I : Kachelinhalt> getInhaltOderNeu(
        position: Position,
        erstelleNeu: () -> I
    ): I = getKachelBei(position).inhalt as? I ?: erstelleNeu()

    /**
     * Setzt den [hamster] ins Territorium.
     *
     * @throws RuntimeException Die [Position des Hamsters][InternerHamster.position] liegt außerhalb des Territoriums.
     * @throws RuntimeException Die [Position des Hamsters][InternerHamster.position] ist blockiert.
     */
    fun spawneHamster(hamster: InternerHamster): InternesTerritorium {
        require(istPositionInnerhalb(hamster.position)) { "ERR_POS_NOT_IN_TERRITORY" }

        return copy(hamster = this.hamster + hamster)
    }

    /**
     * Gibt den [Hamster][InternerHamster] an der [position] zurück.
     *
     * Befindet sich dort kein Hamster, wird `null` zurückgegeben.
     */
    fun getHamsterBei(position: Position): InternerHamster? =
        hamster.find { it.position == position }

    /**
     * Gibt es in diesem Territorium an der [position] einen Hamster?
     */
    fun hatHamsterBei(position: Position): Boolean = hamster.any { it.position == position }

}
