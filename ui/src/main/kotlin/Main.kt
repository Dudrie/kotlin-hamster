import de.github.dudrie.kotlin.hamster.ui.application.HamsterGame
import de.github.dudrie.kotlin.hamster.ui.state.gameViewModel

private fun setGrains() {
    gameViewModel.grainCount.value = 100
}

fun main() {
    HamsterGame().startGame()

    println("Hello world")
    setGrains()
}