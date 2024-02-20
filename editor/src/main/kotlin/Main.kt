import de.github.dudrie.hamster.editor.application.EditorWindow
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * Start the editor in a new window with a default territory size.
 */
fun main() {
    runBlocking {
        EditorWindow().show()

        delay(2000)
    }
}
