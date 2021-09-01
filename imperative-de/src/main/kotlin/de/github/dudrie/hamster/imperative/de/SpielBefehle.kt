package de.github.dudrie.hamster.imperative.de

import de.github.dudrie.hamster.imperative.setGameSpeed
import de.github.dudrie.hamster.imperative.startGame
import de.github.dudrie.hamster.imperative.stopGame

/**
 * Startet ein neues Hamsterspiel.
 *
 * Nachdem das Spiel geladen und gestartet wurde, können Befehle ausgeführt werden. Das Spiel startet pausiert.
 *
 * Es kann immer nur ein Spiel gleichzeitig gestartet sein.
 *
 * @param territoriumsDatei Das Territorium, das geladen werden kann. Wird keines übergeben, so wird das Standardterritorium geladen.
 */
fun starteSpiel(territoriumsDatei: String? = null) {
    startGame(territoriumsDatei)
}

/**
 * Stoppt das aktuelle Spiel.
 *
 * Anschließend können keine weiteren Befehle ausgeführt werden.
 */
fun stoppeSpiel() = stopGame()

/**
 * Ändert die Spielgeschwindigkeit auf die gegebene [geschwindigkeit].
 *
 * Muss zwischen der [minimalen][de.github.dudrie.hamster.internal.model.game.GameCommandStack.minSpeed] und [maximalen][de.github.dudrie.hamster.internal.model.game.GameCommandStack.maxSpeed] Geschwindigkeit liegen.
 */
fun setzeSpielGeschwindigkeit(geschwindigkeit: Float) = setGameSpeed(geschwindigkeit)