package de.github.dudrie.hamster.core.game

import de.github.dudrie.hamster.core.exception.SpielException
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium

/**
 * Bildet den aktuellen Zustand des Spiels ab.
 *
 */
data class SpielZustand(
    /**
     * Das [InternesTerritorium] des Spiels.
     *
     * Gibt es noch keines, so ist es `null`. Sollte über [territorium] angesprochen werden, solange man die `nullable` Eigenschaft nicht benötigt.
     */
    val aktuellesTerritorium: InternesTerritorium? = null,
    /**
     * Die Geschwindigkeit, in der die Simulation ablaufen soll.
     */
    val geschwindigkeit: Double = 4.0,
    /**
     * Der aktuelle [SpielModus] des Spiels.
     */
    val modus: SpielModus = SpielModus.Initialisierung,
    /**
     * Wenn es eine [SpielException] bei der Ausführung eines [de.github.dudrie.hamster.core.game.commands.Kommando]s gab, ist sie hierüber erreichbar.
     *
     * Gab es bisher keine solche [SpielException], ist dieses Attribut `null`.
     */
    val fehler: SpielException? = null,
    /**
     * Liste aller [KommandoErgebnis]se aller bisher ausgeführten und nicht rückgängig gemachten Kommandos.
     */
    val ausgefuhrteKommandos: List<KommandoErgebnis> = listOf(),
    /**
     * Liste aller [KommandoErgebnis]se der Kommandos, die rückgängig gemacht wurden.
     */
    val wiederherstellbareKommandos: List<KommandoErgebnis> = listOf()
) {
    /**
     * Gibt an, ob dieser Spielzustand ein Rückgängigmachen von Kommandos erlaubt.
     */
    val kannRuckgangigMachen: Boolean =
        ausgefuhrteKommandos.isNotEmpty() && istModusFurRuckganigOderWiederherstellen()

    /**
     * Gibt an, ob dieser Spielzustand ein Wiederherstellen von Kommandos erlaubt.
     */
    val kannWiederherstellen: Boolean =
        wiederherstellbareKommandos.isNotEmpty() && istModusFurRuckganigOderWiederherstellen()

    /**
     * Das [aktuellesTerritorium] des Spiels.
     *
     * @throws NullPointerException Das [aktuellesTerritorium] ist `null`.
     */
    val territorium: InternesTerritorium
        get() = aktuellesTerritorium ?: throw NullPointerException("ERR_TERRITORY_IS_NULL")

    /**
     * Erlaubt der aktuelle [modus] ein Rückgängigmachen oder Wiederherstellen?
     */
    private fun istModusFurRuckganigOderWiederherstellen(): Boolean =
        modus == SpielModus.Pausiert
                || modus == SpielModus.Gestoppt
                || modus == SpielModus.Abgebrochen
}
