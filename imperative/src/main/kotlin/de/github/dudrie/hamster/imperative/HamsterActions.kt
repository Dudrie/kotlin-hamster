package de.github.dudrie.hamster.imperative

/**
 * Moves the hamster one tile in the direction it faces.
 *
 * The destination tile must not be blocked.
 *
 * @see isFrontClear
 */
fun move() {
    isGameStarted()
    imperativeGlobalGame?.paule?.move()
}

/**
 * Turns the hamster 90 degrees counterclockwise.
 */
fun turnLeft() {
    isGameStarted()
    imperativeGlobalGame?.paule?.turnLeft()
}

/**
 * The hamster picks up a grain from the tile beneath it.
 *
 * The tile must have at least one grain on it.
 */
fun pickGrain() {
    isGameStarted()
    imperativeGlobalGame?.paule?.pickGrain()
}

/**
 * The hamster drops a grain on the tile beneath it.
 *
 * The hamster must have at least one grain in its mouth.
 *
 * @see isYourMouthEmpty
 */
fun dropGrain() {
    isGameStarted()
    imperativeGlobalGame?.paule?.dropGrain()
}

/**
 * Can the hamster move one tile in its facing direction?
 *
 * If yes this function returns `true`, otherwise `false.
 */
fun isFrontClear(): Boolean {
    isGameStarted()
    return imperativeGlobalGame?.paule?.isFrontClear() ?: false
}

/**
 * Is the hamster's mouth empty?
 *
 * If yes this function returns `true`, otherwise `false`.
 */
fun isYourMouthEmpty(): Boolean {
    isGameStarted()
    return imperativeGlobalGame?.paule?.isYourMouthEmpty() ?: false
}

/**
 * Has the tile on which the hamster sits at least one grain on it?
 *
 * If yes this function returns `true`, otherwise `false.
 */
fun hasYourTileAGrain(): Boolean {
    isGameStarted()
    return imperativeGlobalGame?.paule?.hasYourTileAGrain() ?: false
}

/**
 * The hamster "says" the given [message] printing it to the game log.
 */
fun say(message: String) {
    isGameStarted()
    imperativeGlobalGame?.paule?.say(message)
}
