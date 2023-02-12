package de.github.dudrie.hamster.external.model

import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.datatypes.HamsterLocation
import de.github.dudrie.hamster.interfaces.IHamster
import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.hamster.commands.*

/**
 * Hamster, der im Spiel benutzt wird.
 *
 * Erstellt den zugehörigen [GameHamster] während der Initialisierung.
 *
 * @param territorium [Territorium] des Hamsters.
 * @param ort [HamsterLocation] an dem sich der Hamster befindet. Muss innerhalb des [territorium] liegen.
 * @param richtung [Direction], in die der Hamster anfänglich schaut.
 * @param kornAnzahl Anzahl der Körner, die der Hamster zu Beginn im Mund hat.
 */
class Hamster(val territorium: Territorium, ort: HamsterLocation, richtung: Direction, kornAnzahl: Int) :
    IHamster {

    /**
     * Referenz zum tatsächlichen [GameHamster].
     */
    private val internalHamster: GameHamster

    /**
     * [HamsterSpiel], von welchem dieser Hamster ein Teil ist.
     */
    private val spiel: HamsterSpiel = territorium.spiel

    /**
     * Der aktuelle [HamsterLocation] dieses Hamsters.
     */
    override val ort: HamsterLocation
        get() = internalHamster.tile.location

    /**
     * Die aktuelle [Direction] in die dieser Hamster schaut.
     */
    override val richtung: Direction
        get() = internalHamster.direction

    /**
     * Anzahl der Schritte, die dieser Hamster gemacht hat.
     */
    override val gemachteSchritte: Int
        get() = internalHamster.movesTaken

    init {
        val tile = territorium.holeFeldBei(ort)
        internalHamster =
            GameHamster(
                territory = territorium.internalTerritory,
                tile = tile,
                direction = richtung,
                grainCount = kornAnzahl
            )

        spiel.executeCommand(SpawnHamsterCommand(internalHamster))
    }

    /**
     * Bewegt den Hamster einen Schritt in Blickrichtung.
     *
     * Das Feld vor ihm das nicht blockiert sein.
     */
    override fun laufe() {
        spiel.executeCommand(MoveCommand(internalHamster))
    }

    /**
     * Dreht den Hamster 90 Grad gegen den Uhrzeigersinn.
     */
    override fun dreheNachLinks() {
        spiel.executeCommand(TurnLeftCommand(internalHamster))
    }

    /**
     * Sammelt ein Korn vom Feld des Hamsters auf.
     *
     * Das Feld muss mindestens ein Korn haben, dass aufgesammelt werden kann.
     */
    override fun sammleKornAuf() {
        spiel.executeCommand(PickGrainCommand(internalHamster))
    }

    /**
     * Legt ein Korn auf das Feld des Hamsters.
     *
     * Der Hamster muss mindestens ein Korn im Mund haben.
     */
    override fun legeKornAb() {
        spiel.executeCommand(DropGrainCommand(internalHamster))
    }

    /**
     * Ist das Feld vor dir frei?
     */
    override fun istVorDirFrei(): Boolean {
        val location = internalHamster.getLocationAfterMove()
        return territorium.istFrei(location)
    }

    /**
     * Liegt auf deinem Feld mindestens ein Korn?
     */
    override fun liegtEinKornAufDeinemFeld(): Boolean = territorium.holeAnzahlKoernerBei(ort) > 0

    /**
     * Ist dein Mund leer?
     */
    override fun istDeinMundLeer(): Boolean = internalHamster.grainCount == 0

    /**
     * Gibt eine [nachricht] auf der Spielekonsole aus.
     */
    override fun sage(nachricht: String) {
        spiel.executeCommand(WriteMessageCommand(nachricht))
    }

}
