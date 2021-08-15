package de.github.dudrie.hamster

import de.github.dudrie.hamster.internal.model.game.GameMode
import java.text.MessageFormat
import java.util.*

/**
 * Object with helper methods to display i18n text.
 */
object ResString {

    private val bundle: ResourceBundle = ResourceBundle.getBundle("bundles/hamsterText")

    /**
     * Returns the string associated with the given key.
     *
     * If one needs to replace placeholders inside the string use [getWithFormat] instead. If no associated string could be found a [MissingResourceException] is thrown.
     *
     * @param key Key of the string to get.
     *
     * @return The string for the key.
     *
     * @throws MissingResourceException If no string for the given key could be found.
     *
     * @see ResourceBundle.getString
     */
    fun get(key: String): String = bundle.getString(key)

    /**
     * Returns the string associated with the key and replaces placeholders with the given [replacements].
     *
     * @param key Key of the string to get
     * @param replacements Objects used to replace the placeholders.
     *
     * @return String associated with the given key and filled in placeholders.
     * @throws MissingResourceException If no string for the given key could be found
     *
     * @see MessageFormat.format
     * @see get
     */
    fun getWithFormat(key: String, vararg replacements: Any): String = MessageFormat.format(get(key), *replacements)

    /**
     * Returns a string representation of the given [GameMode].
     *
     * @return String representing the given [GameMode].
     *
     * @see get
     */
    fun getForGameMode(mode: GameMode): String = get(
        when (mode) {
            GameMode.Initializing -> "gamemode.initializing"
            GameMode.Running -> "gamemode.running"
            GameMode.Paused -> "gamemode.paused"
            GameMode.Stopped -> "gamemode.stopped"
        }
    )
}
