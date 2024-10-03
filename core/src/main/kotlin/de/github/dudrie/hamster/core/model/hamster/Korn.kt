package de.github.dudrie.hamster.core.model.hamster

import kotlinx.serialization.Serializable

/**
 * Ein Korn, das der [Hamster][InternerHamster] ins [Inventar][InternerHamster.inventar] nehmen kann.
 */
@Serializable
class Korn : InventarInhalt() {

    /**
     * Prüft, ob es sich um **dasselbe* Korn handelt.
     */
    override fun equals(other: Any?): Boolean {
        return this === other
    }

    /**
     * @see System.identityHashCode
     */
    override fun hashCode(): Int {
        return System.identityHashCode(this)
    }

}
