package de.github.dudrie.hamster.core.model.territory

import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.Kachel
import de.github.dudrie.hamster.core.model.util.Position

data class InternesTerritorium(
    val hamster: List<InternerHamster>,
    val kacheln: Map<Position, Kachel>
) {

    private var _abmessungen: Abmessungen? = null

    val abmessungen: Abmessungen
        get() =
            if (_abmessungen != null) {
                _abmessungen!!
            } else {
                _abmessungen = Abmessungen(
                    breite = kacheln.keys.maxOf { it.x },
                    hohe = kacheln.keys.maxOf { it.y }
                )
                _abmessungen!!
            }

    fun bewegeHamster(hamster: InternerHamster): InternesTerritorium {
        val index = this.hamster.indexOf(hamster)
        require(index != -1) { "ERR_HAMSTER_NOT_IN_TERRITORY" }
        val bewegterHamster = hamster.laufe()
        val neueHamster = this.hamster.toMutableList()

        neueHamster[index] = bewegterHamster
        return copy(hamster = neueHamster)
    }

    fun getKachelBei(position: Position): Kachel {
        return kacheln[position]
            ?: throw NoSuchElementException("ERR_NO_TILE_AT_POSITION")
    }

    fun istPositionInnerhalb(position: Position): Boolean = abmessungen.istInnerhalb(position)

    fun istBlockiert(position: Position): Boolean {
        val kachel = kacheln[position]
        require(kachel != null) { "ERR_NO_TILE_AT_POSITION" }

        return kachel.inhalt?.blocktBewegung ?: false
    }

    fun spawneHamster(hamster: InternerHamster): InternesTerritorium {
        require(istPositionInnerhalb(hamster.position)) { "ERR_POS_NOT_IN_TERRITORY" }
        require(!istBlockiert(hamster.position)) { "ERR_TILE_BLOCKED" }

        return copy(hamster = this.hamster + hamster)
    }

}
