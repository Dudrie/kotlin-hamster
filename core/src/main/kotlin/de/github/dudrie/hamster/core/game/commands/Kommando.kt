package de.github.dudrie.hamster.core.game.commands

import de.github.dudrie.hamster.core.exception.SpielException
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.HamsterString

sealed class Kommando {

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

sealed class HamsterKommando : Kommando() {

    private var _aktualisierterHamster: InternerHamster? = null

    /**
     * Der [InternerHamster] nach dem Ausführen dieses Kommandos
     */
    var aktualisierterHamster: InternerHamster
        get() = _aktualisierterHamster ?: throw IllegalStateException()
        protected set(value) {
            _aktualisierterHamster = value
        }

}
