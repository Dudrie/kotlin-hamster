package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.ResString

class MouthEmptyException : RuntimeException() {
    override fun toString(): String = ResString.get("error.mouth.empty")
}
