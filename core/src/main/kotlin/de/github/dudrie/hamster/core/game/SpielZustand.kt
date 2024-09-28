package de.github.dudrie.hamster.core.game

import de.github.dudrie.hamster.core.exception.SpielException
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium

data class SpielZustand(
    val aktuellesTerritorium: InternesTerritorium? = null,
    val geschwindigkeit: Double = 4.0,
    val modus: SpielModus = SpielModus.Initialisierung,
    val fehler: SpielException? = null,
    val ausgefuhrteKommandos: List<KommandoErgebnis> = listOf(),
    /**
     * Liste aller Kommandos, die rückgängig gemacht wurden.
     */
    val wiederherstellbareKommandos: List<KommandoErgebnis> = listOf()
) {
    val territorium: InternesTerritorium
        get() = aktuellesTerritorium ?: throw NullPointerException("ERR_TERRITORY_IS_NULL")
}
