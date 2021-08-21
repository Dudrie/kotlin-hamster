package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.i18n.HamsterString

/**
 * Indicates that the [GameHamster's][de.github.dudrie.hamster.internal.model.hamster.GameHamster] mouth is empty but an action requires the [GameHamster][de.github.dudrie.hamster.internal.model.hamster.GameHamster] to have a non-empty mouth.
 */
class MouthEmptyException : RuntimeException() {
    /**
     * @return String representation of this exception
     */
    override fun toString(): String = HamsterString.get("error.mouth.empty")
}
