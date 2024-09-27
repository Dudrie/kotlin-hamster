package de.github.dudrie.hamster.core.file

import de.github.dudrie.hamster.core.file.model.KachelDaten
import de.github.dudrie.hamster.core.file.model.StartHamsterDaten
import de.github.dudrie.hamster.core.file.model.StartTerritoriumDaten
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.Kachel
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.Position
import kotlinx.serialization.json.Json

object SpielImporter {

    fun getStartTerritorium(
        dateipfad: String,
        ausResourceOrdner: Boolean = false
    ): InternesTerritorium {
        val json =
            ResourceReader(getDateipfadMitSuffix(dateipfad), ausResourceOrdner).getInhaltAlsText()
        val startDaten = Json.decodeFromString<StartTerritoriumDaten>(json)
        val startHamster = startDaten.startHamster?.let { listOf(getStartHamster(it)) } ?: listOf()

        return InternesTerritorium(
            hamster = startHamster,
            kacheln = erstelleKachelMap(startDaten.kacheln),
            kachelZuMeterSkalierung = startDaten.kachelZuMeterSkalierung
        )
    }

    private fun getStartHamster(daten: StartHamsterDaten): InternerHamster = InternerHamster(
        position = daten.position,
        richtung = daten.richtung,
        inventar = daten.inventar
    )

    private fun erstelleKachelMap(kachelDaten: List<KachelDaten>): Map<Position, Kachel> {
        val kacheln = mutableMapOf<Position, Kachel>()
        kachelDaten.associateByTo(
            destination = kacheln,
            keySelector = { it.position },
            valueTransform = { Kachel(it.inhalt) }
        )

        return kacheln
    }

    private fun getDateipfadMitSuffix(dateipfad: String): String =
        if (dateipfad.endsWith(".json")) {
            dateipfad
        } else {
            "$dateipfad.json"
        }

}
