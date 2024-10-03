package de.github.dudrie.hamster.core.file

import de.github.dudrie.hamster.core.file.model.HamsterDaten
import de.github.dudrie.hamster.core.file.model.KachelDaten
import de.github.dudrie.hamster.core.file.model.StartTerritoriumDaten
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.Kachel
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.Position
import kotlinx.serialization.json.Json
import java.nio.file.Path

object SpielImporter {

    const val STANDARD_DATEIPFAD = "/territories/standard.json"

    fun ladeTerritoriumAusDatei(dateipfad: Path): InternesTerritorium {
        val json = dateipfad.toFile().readText()

        return ladeTerritoriumVonJSON(json)
    }

    fun ladeTerritoriumAusProjekt(
        dateipfad: String,
        ausResourceOrdner: Boolean = false
    ): InternesTerritorium {
        val json =
            ResourceReader(getDateipfadMitSuffix(dateipfad), ausResourceOrdner).getInhaltAlsText()

        return ladeTerritoriumVonJSON(json)
    }

    private fun ladeTerritoriumVonJSON(json: String): InternesTerritorium {
        val startDaten = Json.decodeFromString<StartTerritoriumDaten>(json)

        return InternesTerritorium(
            hamster = startDaten.hamster.map(::konvertiereHamsterDaten),
            kacheln = erstelleKachelMap(startDaten.kacheln),
            kachelZuMeterSkalierung = startDaten.kachelZuMeterSkalierung
        )
    }

    private fun konvertiereHamsterDaten(daten: HamsterDaten): InternerHamster = InternerHamster(
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
