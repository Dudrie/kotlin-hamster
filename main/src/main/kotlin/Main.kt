import de.github.dudrie.hamster.external.model.HamsterSpiel
import de.github.dudrie.hamster.external.model.EinfachesHamsterSpiel

private class TestHamsterSpiel : EinfachesHamsterSpiel("testTer", 2) {

    override fun fuehreAus() {
        paule.laufe()
        paule.laufe()
        paule.dreheNachLinks()
        paule.sammleKornAuf()
        paule.laufe()
    }

}

private fun runSampleGame(game: HamsterSpiel) {
    game.gameCommands.setSpeed(7f)
    val paule = game.paule

    repeat(3) {
        paule.sammleKornAuf()
    }
    paule.laufe()

    repeat(5) {
        paule.sage("I said something :) #$it")
    }

    repeat(3) {
        paule.dreheNachLinks()
    }

    repeat(2) {
        paule.laufe()
    }

    repeat(2) {
        paule.legeKornAb()
    }
    paule.laufe()
}

internal fun main() {
    val game = TestHamsterSpiel()
    game.lasseSpielLaufen()
}
