package de.github.dudrie.hamster.imperative

fun move() {
    isGameStarted()
    imperativeGlobalGame?.paule?.move()
}

fun turnLeft() {
    isGameStarted()
    imperativeGlobalGame?.paule?.turnLeft()
}

fun pickGrain() {
    isGameStarted()
    imperativeGlobalGame?.paule?.pickGrain()
}

fun dropGrain() {
    isGameStarted()
    imperativeGlobalGame?.paule?.dropGrain()
}

fun canYouMove() {
    isGameStarted()
    imperativeGlobalGame?.paule?.canYouMove()
}

fun isYourMouthEmpty() {
    isGameStarted()
    imperativeGlobalGame?.paule?.isYourMouthEmpty()
}

fun hasYourTileAGrain() {
    isGameStarted()
    imperativeGlobalGame?.paule?.hasYourTileAGrain()
}

fun say(message: String) {
    isGameStarted()
    imperativeGlobalGame?.paule?.say(message)
}
