package de.github.dudrie.hamster.core.game.commands

import de.github.dudrie.hamster.core.exception.SpielException
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.HamsterString

/**
 * Beschreibt ein Kommando, welches im Hamsterspiel ausgeführt werden kann.
 */
abstract class Kommando {

    /**
     * Führt das Kommando aus und gibt das [InternesTerritorium] zurück, welches durch die Ausführung des Kommandos entsteht.
     *
     * @throws SpielException Kann das Kommando nicht ausgeführt werden, wird eine [SpielException] geworfen.
     */
    abstract fun fuhreAus(territorium: InternesTerritorium): InternesTerritorium

    /**
     * Erzeugt einen [HamsterString], welcher dieses Kommando beschreibt.
     */
    abstract fun getLogNachricht(): HamsterString

}

/**
 * Beschreibt ein Kommando, welches einen [InternerHamster] betrifft.
 */
abstract class HamsterKommando : Kommando() {

    /**
     * Der [InternerHamster] nach dem Ausführen dieses Kommandos
     */
    lateinit var aktualisierterHamster: InternerHamster
        protected set

}
