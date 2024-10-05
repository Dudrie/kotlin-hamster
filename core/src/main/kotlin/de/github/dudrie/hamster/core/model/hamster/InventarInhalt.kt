package de.github.dudrie.hamster.core.model.hamster

import kotlinx.serialization.Serializable

/**
 * Beschreibt den Inhalt, den der [Hamster][InternerHamster] im [Inventar][InternerHamster.inventar] haben kann.
 */
@Serializable
sealed class InventarInhalt
