import de.github.dudrie.hamster.ui.application.HamsterGameCompose
import de.github.dudrie.hamster.ui.state.GameViewModel

private fun setGrains() {
    Thread.sleep(1000L)
    repeat(40) {
        GameViewModel.model.hamsterGame.paule.pickGrain()
        Thread.sleep(250L)
    }
}

fun main() {
    HamsterGameCompose().startGame()

    println("Hello world")
    setGrains()
}
