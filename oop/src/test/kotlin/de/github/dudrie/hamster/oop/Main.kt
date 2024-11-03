package de.github.dudrie.hamster.oop

import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.core.model.util.Richtung
import de.github.dudrie.hamster.oop.model.EinfachesHamsterSpiel
import de.github.dudrie.hamster.oop.model.Hamster

fun main() {
    val spiel = object : EinfachesHamsterSpiel() {
        override fun fuehreAus() {
            paule.laufe()
            paule.laufe()
            paule.dreheNachLinks()

            paule.sammleKornAuf()

            val paula = Hamster(paule.territorium, Position(1, 1), Richtung.Westen, 0)

            paula.dreheNachLinks()
            paula.dreheNachLinks()

            paule.dreheNachLinks()
            paule.laufe()
            //paule.laufe()
            println("Korn auf Feld (false): ${paule.liegtEinKornAufDeinemFeld()}")
            paule.legeKornAb()
            println("Korn auf Feld (true): ${paule.liegtEinKornAufDeinemFeld()}")

            println("Ist frei (false): ${paule.istVorDirFrei()}")
            paule.dreheNachLinks()
            println("Ist frei (false): ${paule.istVorDirFrei()}")
            paule.dreheNachLinks()
            println("Ist frei (true): ${paule.istVorDirFrei()}")

            println("Mund leer (true): ${paule.istDeinMundLeer()}")
            paule.sammleKornAuf()
            println("Mund leer (false): ${paule.istDeinMundLeer()}")
            paule.legeKornAb()

            paule.sage("Puh, das wäre geschafft.")
        }
    }

    spiel.lasseSpielAblaufen()
}
