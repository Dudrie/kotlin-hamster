import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.external.model.EditableTerritory
import de.github.dudrie.hamster.external.model.HamsterGame
import de.github.dudrie.hamster.ui.application.windows.EditorWindow
import java.util.concurrent.CountDownLatch

private fun runSampleGame(game: HamsterGame) {
    game.gameCommands.setSpeed(7f)
    val paule = game.paule

    repeat(3) {
        paule.pickGrain()
    }
    paule.move()

    repeat(25) {
        paule.talk("I said something :) #$it")
    }

    repeat(3) {
        paule.turnLeft()
    }
    repeat(2) {
        paule.move()
    }

    repeat(2) {
        paule.dropGrain()
    }
    paule.move()
}

internal fun startEditor() {
    EditorWindow(EditableTerritory(Size(5, 3))).show(
        CountDownLatch((1))
    )
}

internal fun main() {
    startEditor()

//    val game = HamsterGame("/territories/testTer01.json")
//    game.startGame(false)
//
//    runSampleGame(game)
}
