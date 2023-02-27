package de.github.dudrie.hamster.interfaces

import de.github.dudrie.hamster.datatypes.Richtung
import de.github.dudrie.hamster.datatypes.SpielOrt

/**
 * Helper interface for the hamster used during a game.
 */
interface IHamster {

    /**
     * Current location of the hamster.
     */
    val ort: SpielOrt

    /**
     * Current direction the hamster is facing.
     */
    val richtung: Richtung

    /**
     * Number of moves the hamster has taken.
     */
    val gemachteSchritte: Int

    /**
     * Moves the hamster one step.
     */
    fun laufe()

    /**
     * Turns the hamster 90 degrees counterclockwise.
     */
    fun dreheNachLinks()

    /**
     * Picks up a grain from the tile the hamster currently stands on.
     */
    fun sammleKornAuf()

    /**
     * Drops a grain unto the tile the hamster currently stand on.
     */
    fun legeKornAb()

    /**
     * Is the tile in front of the hamster free for movement?
     */
    fun istVorDirFrei(): Boolean

    /**
     * Has the tile the hamster stands on at least one grain?
     */
    fun liegtEinKornAufDeinemFeld(): Boolean

    /**
     * Is the mouth of the hamster empty?
     */
    fun istDeinMundLeer(): Boolean

    /**
     * Prints a [nachricht] to the game's console.
     */
    fun sage(nachricht: String)

}
