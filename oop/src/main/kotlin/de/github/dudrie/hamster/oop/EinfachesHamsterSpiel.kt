package de.github.dudrie.hamster.oop

import de.github.dudrie.hamster.core.game.SpielViewModel
import de.github.dudrie.hamster.ui.model.HamsterSpiel
import de.github.dudrie.hamster.ui.windows.SpielFenster
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class EinfachesHamsterSpiel(private val territoriumsDatei: String? = null) :
    HamsterSpiel() {

    override val spiel = SpielViewModel()

    lateinit var paule: Hamster
        private set

    fun starteSpiel() {
        val job = CoroutineScope(Dispatchers.IO).launch {
            spiel.ladeSpiel(territoriumsDatei)

            val territorium = Territorium(spiel, spiel.territorium)
            paule = Hamster(territorium, spiel.standardHamster)
        }

        SpielFenster(job, this).starte()
    }

}
