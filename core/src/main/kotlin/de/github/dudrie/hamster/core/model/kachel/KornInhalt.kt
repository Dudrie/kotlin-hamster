package de.github.dudrie.hamster.core.model.kachel

data class KornInhalt(val anzahl: Int) : Kachelinhalt() {
    override val blocktBewegung: Boolean = false
}
