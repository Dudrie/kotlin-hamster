package de.github.dudrie.hamster.imperative.de

import de.github.dudrie.hamster.imperative.*

/**
 * Lässt den Hamster ein Feld in Blickrichtung laufen.
 *
 * Das Zielfeld darf nicht blockiert sein.
 *
 * @see istVorDirFrei
 */
fun laufe() = move()

/**
 * Dreht den Hamster 90° gegen den Uhrzeigersinn.
 */
fun dreheNachLinks() = turnLeft()

/**
 * Der Hamster sammelt ein Korn von seinem Feld auf.
 *
 * Auf dem Feld muss mindestens ein Korn liegen.
 */
fun sammleKornAuf() = pickGrain()

/**
 * Der Hamster legt ein Korn auf sein Feld ab.
 *
 * Er muss mindestens ein Korn im Mund haben.
 *
 * @see istDeinMundLeer
 */
fun legeKornAb() = dropGrain()

/**
 * Kann der Hamster sich auf das Feld direkt vor ihm bewegen?
 */
fun istVorDirFrei(): Boolean = isFrontClear()

/**
 * Ist der Mund des Hamsters leer?
 */
fun istDeinMundLeer(): Boolean = isYourMouthEmpty()

/**
 * Liegt auf dem Feld des Hamsters mindestens ein Korn?
 */
fun liegtEinKornAufDeinemFeld(): Boolean = hasYourTileAGrain()

/**
 * Der Hamster "sagt" die übergebene [nachricht].
 *
 * Sie wird in der Spielkonsole ausgegeben.
 */
fun sage(nachricht: String) = say(nachricht)

/**
 * Gibt die Anzahl der vom Hamster zurückgelegten Schritte zurück.
 */
fun holeAnzahlSchritte(): Int = getNumberOfHamsterMovesTaken()
