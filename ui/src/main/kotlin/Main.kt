import de.github.dudrie.kotlin.hamster.ui.application.HamsterGameCompose
import de.github.dudrie.kotlin.hamster.ui.state.GameViewModel

private fun setGrains() {
    GameViewModel.model.hamsterGame.paule.grainCountState.value = 123
}

fun main() {
    HamsterGameCompose().startGame()

    println("Hello world")
    setGrains()
}
