package de.github.dudrie.hamster.editor.components.dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CompletableDeferred

class DialogResult<T> {
    /**
     * Handles the waiting and storing the result.
     */
    private var onResult: CompletableDeferred<T>? by mutableStateOf(null)

    /**
     * Waits until a result is set via [setResult].
     */
    suspend fun awaitResult(): T {
        onResult = onResult ?: CompletableDeferred()
        val result = onResult!!.await()
        onResult = null
        return result
    }

    /**
     * Sets the [result] of this state if there is at least one awaiting the result.
     *
     * Makes [awaitResult] return with the given [result].
     */
    fun setResult(result: T) {
        onResult?.complete(result)
    }
}
