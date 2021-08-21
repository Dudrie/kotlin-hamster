import de.github.dudrie.hamster.imperative.*

internal fun main() {
    startGame()
    setGameSpeed(6f)

    stopGame()
    say("Hello imperative world!!")
    move()
    move()
    pickGrain()

    stopGame()
}
