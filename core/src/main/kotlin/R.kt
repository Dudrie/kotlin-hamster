package de.github.dudrie.hamster

import de.github.dudrie.hamster.internal.model.game.GameMode
import java.text.MessageFormat
import java.util.*

object ResString {

    private val bundle: ResourceBundle = ResourceBundle.getBundle("bundles/hamsterText")

    fun get(key: String): String = bundle.getString(key)

    fun getWithFormat(key: String, vararg replacements: Any): String = MessageFormat.format(get(key), *replacements)

    fun getForGameMode(mode: GameMode): String = get(
        when (mode) {
            GameMode.Initializing -> "gamemode.initializing"
            GameMode.Running -> "gamemode.running"
            GameMode.Paused -> "gamemode.paused"
            GameMode.Stopped -> "gamemode.stopped"
        }
    )
}
