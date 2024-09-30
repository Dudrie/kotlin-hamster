@file:Suppress("unused")

package de.github.dudrie.hamster.functional

/**
 * Lässt den Hamster ein Feld in Blickrichtung laufen.
 *
 * Das Zielfeld darf nicht blockiert sein.
 *
 * @see istVorDirFrei
 */
fun laufe() {
    SingletonSpiel.paule.laufe()
}

/**
 * Dreht den Hamster 90° gegen den Uhrzeigersinn.
 */
fun dreheNachLinks() {
    SingletonSpiel.paule.dreheNachLinks()
}

/**
 * Der Hamster sammelt ein Korn von seinem Feld auf.
 *
 * Auf dem Feld muss mindestens ein Korn liegen.
 */
fun sammleKornAuf() {
    SingletonSpiel.paule.sammleKornAuf()
}

/**
 * Der Hamster legt ein Korn auf sein Feld ab.
 *
 * Er muss mindestens ein Korn im Mund haben.
 *
 * @see istDeinMundLeer
 */
fun legeKornAb() {
    SingletonSpiel.paule.legeKornAb()
}

/**
 * Kann der Hamster sich auf das Feld direkt vor ihm bewegen?
 */
fun istVorDirFrei(): Boolean = SingletonSpiel.paule.istVorDirFrei()

/**
 * Liegt auf dem Feld des Hamsters mindestens ein Korn?
 */
fun liegtEinKornAufDeinemFeld(): Boolean = SingletonSpiel.paule.liegtEinKornAufDeinemFeld()

/**
 * Ist der Mund des Hamsters leer?
 */
fun istDeinMundLeer(): Boolean = SingletonSpiel.paule.istDeinMundLeer()

/**
 * Der Hamster "sagt" die übergebene [nachricht].
 *
 * Sie wird in der Spielkonsole ausgegeben.
 */
fun sage(nachricht: String) {
    SingletonSpiel.paule.sage(nachricht)
}
