package de.github.dudrie.hamster.core.game.commands

import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.HamsterStringId

/**
 * Die [nachricht] wird als Text in der Spielkonsole angezeigt.
 */
class SageEtwasKommando(private val nachricht: String) : Kommando() {

    /**
     * Das [territorium] bleibt bei der Ausführung dieses Kommandos unverändert.
     */
    override fun fuhreAus(territorium: InternesTerritorium): InternesTerritorium = territorium

    override fun getLogNachricht(): HamsterString =
        HamsterString(HamsterStringId.KOMMANDO_HAMSTER_SAGE, nachricht)

}
