import de.github.dudrie.hamster.editor.application.EditorWindow
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CountDownLatch

/**
 * Start the editor in a new window with a default territory size.
 */
fun main() {
    runBlocking {
        EditorWindow().show(CountDownLatch(1))

        delay(2000)
    }
}
