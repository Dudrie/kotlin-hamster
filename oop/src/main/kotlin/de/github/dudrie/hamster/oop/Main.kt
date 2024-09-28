package de.github.dudrie.hamster.oop

import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.core.model.util.Richtung
import de.github.dudrie.hamster.oop.model.EinfachesHamsterSpiel
import de.github.dudrie.hamster.oop.model.Hamster

fun main() {
    val spiel = object : EinfachesHamsterSpiel() {}
    val paule = spiel.paule

    spiel.starteSpiel()
    paule.laufe()
    paule.laufe()
    paule.dreheNachLinks()

    paule.sammleKornAuf()

    println(paule.anzahlKorner)
    val paula = Hamster(paule.territorium, Position(1, 1), Richtung.Westen, 0)

    paula.dreheNachLinks()
    paula.dreheNachLinks()

    paule.dreheNachLinks()
    paule.laufe()

    spiel.stoppeSpiel()
}
