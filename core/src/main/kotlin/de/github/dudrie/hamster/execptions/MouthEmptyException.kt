package de.github.dudrie.hamster.execptions

class MouthEmptyException : RuntimeException() {
    override fun toString(): String = "Cannot drop any grain because the hamster's mouth is empty."
}
