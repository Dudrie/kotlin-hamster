package de.github.dudrie.hamster.oop

import de.github.dudrie.hamster.oop.model.EinfachesHamsterSpiel

fun main() {
    val spiel = object : EinfachesHamsterSpiel() {}

    spiel.starteSpiel()
}
