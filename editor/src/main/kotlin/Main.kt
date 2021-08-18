import de.github.dudrie.hamster.editor.application.EditorWindow
import java.util.concurrent.CountDownLatch

/**
 * Start the editor in a new window with a default territory size.
 */
fun main() {
    EditorWindow().show(CountDownLatch(1))
}
