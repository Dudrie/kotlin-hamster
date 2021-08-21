import de.github.dudrie.hamster.imperative.*

internal fun main() {
    startGame()
    setGameSpeed(6f)

    say("Hello imperative world!!")
    move()
    move()
    pickGrain()

    stopGame()
}
