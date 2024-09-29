package de.github.dudrie.hamster.core.game.commands

import de.github.dudrie.hamster.core.game.SpielViewModel
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.HamsterString

class SageEtwasKommando(private val nachricht: String) : Kommando() {

    override fun fuhreAus(territorium: InternesTerritorium): InternesTerritorium = territorium

    override fun getLogNachricht(): HamsterString =
        HamsterString("kommando.hamster.sage", nachricht)

}
