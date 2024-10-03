@file:Suppress("unused")

package de.github.dudrie.hamster.functional

/**
 * Lässt Paule ein Feld in Blickrichtung laufen.
 *
 * Das Zielfeld darf nicht blockiert sein.
 *
 * @see istVorDirFrei
 */
fun laufe() {
    SingletonSpiel.paule.laufe()
}

/**
 * Dreht Paule 90° gegen den Uhrzeigersinn.
 */
fun dreheNachLinks() {
    SingletonSpiel.paule.dreheNachLinks()
}

/**
 * Paule sammelt ein Korn von seinem Feld auf.
 *
 * Auf dem Feld muss mindestens ein Korn liegen.
 */
fun sammleKornAuf() {
    SingletonSpiel.paule.sammleKornAuf()
}

/**
 * Paule legt ein Korn auf sein Feld ab.
 *
 * Er muss mindestens ein Korn im Mund haben.
 *
 * @see istDeinMundLeer
 */
fun legeKornAb() {
    SingletonSpiel.paule.legeKornAb()
}

/**
 * Kann Paule sich auf das Feld direkt vor ihm bewegen?
 */
fun istVorDirFrei(): Boolean = SingletonSpiel.paule.istVorDirFrei()

/**
 * Liegt auf dem Feld von Paule mindestens ein Korn?
 */
fun liegtEinKornAufDeinemFeld(): Boolean = SingletonSpiel.paule.liegtEinKornAufDeinemFeld()

/**
 * Ist der Mund von Paule leer?
 */
fun istDeinMundLeer(): Boolean = SingletonSpiel.paule.istDeinMundLeer()

/**
 * Paule "sagt" die übergebene [nachricht].
 *
 * Sie wird in der Spielkonsole ausgegeben.
 */
fun sage(nachricht: String) {
    SingletonSpiel.paule.sage(nachricht)
}

/**
 * Gibt die Anzahl an Schritten, die Paule bisher gemacht hat, zurück.
 */
fun getAnzahlSchritte(): Int = SingletonSpiel.paule.anzahlSchritte
