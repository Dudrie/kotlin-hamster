package de.github.dudrie.hamster.editor.model

import java.io.IOException

/**
 * Indicates the result of an IO operation.
 *
 * @param error The exception thrown during the operation. If `null` the operation did not throw an exception.
 */
data class IOOperationResult(val error: IOException? = null) {
    /**
     * Indicates whether the operation was a success or not.
     *
     * If `true` the [error] is `null`, if `false` the [error] is not `null`.
     */
    val isSuccess: Boolean
        get() = error == null
}
