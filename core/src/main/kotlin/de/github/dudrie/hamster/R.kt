package de.github.dudrie.hamster

import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.internal.model.game.GameMode
import de.github.dudrie.hamster.internal.model.territory.GameTileType
import java.text.MessageFormat
import java.util.*

/**
 * Object with helper methods to display i18n text.
 */
object ResString {

    private val bundle: ResourceBundle = ResourceBundle.getBundle("bundles/hamsterText")

    /**
     * Returns the string associated with the given [key].
     *
     * If one needs to replace placeholders inside the string use [getWithFormat] instead. If no associated string could be found a [MissingResourceException] is thrown.
     *
     * @throws MissingResourceException If no string for the given key could be found.
     *
     * @see ResourceBundle.getString
     */
    fun get(key: String): String = bundle.getString(key)

    /**
     * Returns the string associated with the [key] and replaces placeholders with the given [replacements].
     *
     * @throws MissingResourceException If no string for the given key could be found
     *
     * @see MessageFormat.format
     * @see get
     */
    fun getWithFormat(key: String, vararg replacements: Any): String = MessageFormat.format(get(key), *replacements)

    /**
     * Returns the localized string representation of the given [GameMode].
     *
     * @see get
     */
    fun getForGameMode(mode: GameMode): String = get(
        when (mode) {
            GameMode.Initializing -> "gamemode.initializing"
            GameMode.Running -> "gamemode.running"
            GameMode.Paused -> "gamemode.paused"
            GameMode.Aborted -> "gamemode.aborted"
            GameMode.Stopped -> "gamemode.stopped"
        }
    )

    /**
     * Returns the localized string representation of the given [direction].
     */
    fun getForDirection(direction: Direction): String = get(
        when (direction) {
            Direction.North -> "direction.north"
            Direction.East -> "direction.east"
            Direction.South -> "direction.south"
            Direction.West -> "direction.west"
        }
    )

    /**
     * Returns the localized string representation of the given [GameTileType].
     */
    fun getForGameTileType(type: GameTileType): String = get(
        when (type) {
            GameTileType.Floor -> "tile.type.floor"
            GameTileType.Wall -> "tile.type.wall"
        }
    )
}
