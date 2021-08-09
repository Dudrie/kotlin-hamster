import de.github.dudrie.kotlin.hamster.ui.application.HamsterGameUI
import de.github.dudrie.kotlin.hamster.ui.state.gameViewModel

private fun setGrains() {
    gameViewModel.hamsterGame.paule.grainCountState.value = 123
}

fun main() {
    HamsterGameUI().startGame()

    println("Hello world")
    setGrains()
}
