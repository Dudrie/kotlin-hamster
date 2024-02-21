package de.github.dudrie.hamster.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.github.dudrie.hamster.datatypes.Richtung
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.datatypes.SpielOrt
import de.github.dudrie.hamster.importer.GameImporter
import de.github.dudrie.hamster.interfaces.AbstraktesHamsterSpiel
import de.github.dudrie.hamster.interfaces.AbstraktesTerritorium
import de.github.dudrie.hamster.interfaces.IHamster
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.game.GameCommandStack
import de.github.dudrie.hamster.internal.model.game.GameMode
import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.hamster.commands.*
import de.github.dudrie.hamster.internal.model.territory.GameTerritory
import de.github.dudrie.hamster.internal.model.territory.GameTile

class ChangingHamsterGame(initialTerritory: String, private val loadFromOuterModule: Boolean = true) :
    AbstraktesHamsterSpiel() {

    override var gameCommands: GameCommandStack = GameCommandStack()
        private set

    override lateinit var territorium: ChangingTerritory
        private set

    lateinit var paule: ChangingHamster
        private set

    override var isInitialized by mutableStateOf(false)
        private set

    init {
        changeGame(initialTerritory)
    }

    fun changeGame(territoryFile: String) {
        isInitialized = false
        gameCommands.reset()
        gameCommands.mode = GameMode.Initializing

        val importer = GameImporter(territoryFile, loadFromOuterModule)

        territorium = ChangingTerritory(importer.gameTerritory)
        paule = ChangingHamster(importer.gameHamster, this)

        gameCommands.executeCommand(SpawnHamsterCommand(importer.gameHamster))

        gameCommands.startGame(false)
        isInitialized = true
    }

    override fun starteSpiel(startePausiert: Boolean) {
        gameCommands.startGame(startePausiert)
    }

    override fun executeCommand(command: Command) {
        gameCommands.executeCommand(command)
    }
}

class ChangingTerritory(private val internal: GameTerritory) : AbstraktesTerritorium() {

    override val abmessungen: Size = internal.size

    override val feldZuMeterSkalierung: Double = internal.tileToMeterScaling

    override fun holeFeldBei(ort: SpielOrt): GameTile = internal.getTileAt(ort)

}

class ChangingHamster(private val internalHamster: GameHamster, private val spiel: ChangingHamsterGame) : IHamster {

    /**
     * Der aktuelle [SpielOrt] dieses Hamsters.
     */
    override val ort: SpielOrt
        get() = internalHamster.tile.location

    /**
     * Die aktuelle [Richtung] in die dieser Hamster schaut.
     */
    override val richtung: Richtung
        get() = internalHamster.direction

    /**
     * Anzahl der Schritte, die dieser Hamster gemacht hat.
     */
    override val gemachteSchritte: Int
        get() = internalHamster.movesTaken

    private val territory: ChangingTerritory = spiel.territorium

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
        return territory.istFrei(location)
    }

    /**
     * Liegt auf deinem Feld mindestens ein Korn?
     */
    override fun liegtEinKornAufDeinemFeld(): Boolean = territory.holeAnzahlKoernerBei(ort) > 0

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
