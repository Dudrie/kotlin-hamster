package de.github.dudrie.hamster.oop.model

import de.github.dudrie.hamster.core.file.SpielExporter
import de.github.dudrie.hamster.core.game.SpielViewModel
import de.github.dudrie.hamster.ui.windows.SpielFenster
import kotlinx.coroutines.*

abstract class EinfachesHamsterSpiel(private val territoriumsDatei: String? = null) {

    private val spiel = SpielViewModel()

    lateinit var paule: Hamster
        private set

    private var fenster: SpielFenster = SpielFenster(spiel) {
        spiel.ladeSpiel(territoriumsDatei)
        spiel.erstelleStandardTerritorium()
        SpielExporter.speichereSpiel(
            "./core/src/main/resources/territories/standard.json",
            spiel.territorium
        )

        val territorium = Territorium(spiel, spiel.territorium)
        paule = Hamster(territorium, spiel.standardHamster)
    }

    init {
        runBlocking { fenster.starte() }
    }

    fun starteSpiel(startePausiert: Boolean = false) {
        runBlocking {
            spiel.starteSpiel(startePausiert)
        }
    }

    fun stoppeSpiel() {
        runBlocking {
            spiel.stoppeSpiel()
        }
    }

}
