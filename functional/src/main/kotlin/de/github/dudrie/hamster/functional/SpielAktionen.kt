@file:Suppress("unused")

package de.github.dudrie.hamster.functional

/**
 * Lädt und startet ein neues Hamsterspiel.
 *
 * Dabei wird die übergebene [territoriumsDatei] geladen.
 */
fun starteSpiel(territoriumsDatei: String?) {
    SingletonSpiel.ladeSpiel(territoriumsDatei)
}

/**
 * Lädt und startet ein neues Hamsterspiel.
 *
 * Dabei wird die übergebene [territoriumsDatei] geladen und das Spiel wird direkt [pausiert][de.github.dudrie.hamster.core.game.SpielModus.Pausiert].
 *
 * @see starteSpiel
 */
fun starteSpielPausiert(territoriumsDatei: String?) {
    SingletonSpiel.ladeSpielPausiert(territoriumsDatei)
}

/**
 * [Stoppt][de.github.dudrie.hamster.core.game.SpielModus.Gestoppt] das aktuell geladene Hamsterspiel.
 *
 * Nach dem Stoppen können **keine** weiteren Kommandos erteilt werden.
 */
fun stoppeSpiel() {
    SingletonSpiel.spiel.stoppeSpiel()
}

/**
 * [Pausiert][de.github.dudrie.hamster.core.game.SpielModus.Pausiert] das aktuell geladene Hamsterspiel.
 *
 * Um das Spiel fortzusetzen, muss [setzeSpielFort] aufgerufen werden.
 */
fun pausiereSpiel() {
    SingletonSpiel.spiel.pausiereSpiel()
}

/**
 * Setzt das aktuell geladene Hamsterspiel fort.
 *
 * Das Spiel nimmt ab jetzt wieder Kommandos entgegen.
 *
 * @throws IllegalArgumentException Wenn das Spiel nicht [pausiert][de.github.dudrie.hamster.core.game.SpielModus.Pausiert] ist.
 */
fun setzeSpielFort() {
    SingletonSpiel.spiel.setzeSpielFort()
}

/**
 * Setzt die [geschwindigkeit] des aktuell geladenen Hamsterspiels auf den gegebenen Wert.
 */
fun setGeschwindigkeit(geschwindigkeit: Double) {
    SingletonSpiel.spiel.setGeschwindigkeit(geschwindigkeit)
}
