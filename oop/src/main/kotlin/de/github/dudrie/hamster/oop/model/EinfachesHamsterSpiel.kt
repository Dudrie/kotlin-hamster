package de.github.dudrie.hamster.oop.model

import de.github.dudrie.hamster.core.file.SpielExporter
import de.github.dudrie.hamster.core.game.SpielViewModel
import de.github.dudrie.hamster.ui.windows.SpielFenster
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class EinfachesHamsterSpiel(private val territoriumsDatei: String? = null) {

    private val spiel = SpielViewModel()

    lateinit var paule: Hamster
        private set

    fun starteSpiel() {
        val job = CoroutineScope(Dispatchers.IO).launch {
            spiel.ladeSpiel(territoriumsDatei)
            spiel.erstelleStandardTerritorium()
            SpielExporter.speichereSpiel(
                "./core/src/main/resources/territories/standard.json",
                spiel.territorium
            )

            val territorium = Territorium(spiel, spiel.territorium)
            paule = Hamster(territorium, spiel.standardHamster)
        }

        SpielFenster(job, spiel).starte()
    }

}
