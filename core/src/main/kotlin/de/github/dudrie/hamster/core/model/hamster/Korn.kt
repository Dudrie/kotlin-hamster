package de.github.dudrie.hamster.core.model.hamster

import kotlinx.serialization.Serializable

@Serializable
class Korn : InventarInhalt() {
    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun hashCode(): Int {
        return System.identityHashCode(this)
    }
}