import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.editor.application.EditorWindow
import de.github.dudrie.hamster.editor.model.EditableTerritory
import java.util.concurrent.CountDownLatch

fun main() {
    EditorWindow(EditableTerritory(Size(5, 3))).show(CountDownLatch(1))
}
