package de.github.dudrie.hamster.core.model.territory

import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.Kachel
import de.github.dudrie.hamster.core.model.kachel.Kachelinhalt
import de.github.dudrie.hamster.core.model.util.Position

data class InternesTerritorium(
    val hamster: List<InternerHamster>,
    val kacheln: Map<Position, Kachel>,
    val kachelZuMeterSkalierung: Double = 1.0
) {

    private var _abmessungen: Abmessungen? = null

    val abmessungen: Abmessungen
        get() =
            if (_abmessungen != null) {
                _abmessungen!!
            } else {
                _abmessungen = Abmessungen(
                    breite = kacheln.keys.maxOf { it.x } + 1,
                    hohe = kacheln.keys.maxOf { it.y } + 1
                )
                _abmessungen!!
            }

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

    fun getKachelBei(position: Position): Kachel {
        return kacheln[position]
            ?: throw NoSuchElementException("ERR_NO_TILE_AT_POSITION")
    }

    fun ersetzeKachel(position: Position, neueKachel: Kachel): InternesTerritorium {
        require(kacheln.containsKey(position)) { "ERR_NO_TILE_FOR_POSITION" }
        val neueKacheln = kacheln.toMutableMap()
        neueKacheln[position] = neueKachel
        return copy(kacheln = neueKacheln)
    }

    fun ersetzeKachelInhalt(position: Position, neuerInhalt: Kachelinhalt): InternesTerritorium {
        val kachel = kacheln[position] ?: throw RuntimeException("ERR_NO_TILE_FOR_POSITION")
        return ersetzeKachel(position, kachel.copy(inhalt = neuerInhalt))
    }

    fun istPositionInnerhalb(position: Position): Boolean = abmessungen.istInnerhalb(position)

    fun istBlockiert(position: Position): Boolean {
        val kachel = kacheln[position]
        require(kachel != null) { "ERR_NO_TILE_AT_POSITION" }

        return kachel.inhalt.blocktBewegung || hatHamsterBei(position)
    }

    inline fun <reified I : Kachelinhalt> hatInhalt(position: Position): Boolean =
        getKachelBei(position).inhalt is I

    inline fun <reified I : Kachelinhalt> getInhalt(position: Position): I =
        getKachelBei(position).inhalt as? I
            ?: throw IllegalArgumentException("ERR_NO_MATCHING_TILE_CONTENT")

    fun spawneHamster(hamster: InternerHamster): InternesTerritorium {
        require(istPositionInnerhalb(hamster.position)) { "ERR_POS_NOT_IN_TERRITORY" }
        require(!istBlockiert(hamster.position)) { "ERR_TILE_BLOCKED" }

        return copy(hamster = this.hamster + hamster)
    }

    fun getHamsterBei(position: Position): InternerHamster? =
        hamster.find { it.position == position }

    fun hatHamsterBei(position: Position): Boolean = hamster.any { it.position == position }

}
