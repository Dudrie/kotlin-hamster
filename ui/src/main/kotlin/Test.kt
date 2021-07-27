import javafx.application.Application
import javafx.stage.Stage

class HamsterApp : Application() {
    override fun start(primaryStage: Stage?) {
        if (primaryStage == null) {
            return
        }

        primaryStage.width = 600.0
        primaryStage.height = 400.0
        primaryStage.minWidth = 600.0
        primaryStage.minHeight = 400.0
        primaryStage.show()
    }

}

fun main() {
    Application.launch(HamsterApp::class.java)
}