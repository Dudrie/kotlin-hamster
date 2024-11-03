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

/**
 * Importiert ein Spiel in Form eines [InternesTerritorium]s.
 */
object SpielImporter {

    /**
     * Dateipfad, unter dem das Standardterritorium der Bibliothek gefunden werden kann.
     */
    const val STANDARD_DATEIPFAD = "/territories/standard.json"

    /**
     * Lädt das [InternesTerritorium] aus der Datei am gegebenen [dateipfad].
     *
     * Die Datei darf sich dabei nicht im Projekt und nicht innerhalb der Bibliothek befinden. Für diese Fälle benutzt man [ladeTerritoriumAusProjekt].
     */
    fun ladeTerritoriumAusDatei(dateipfad: Path): InternesTerritorium {
        val json = dateipfad.toFile().readText()

        return ladeTerritoriumVonJSON(json)
    }

    /**
     * Lädt das [InternesTerritorium] aus einer Datei innerhalb des Projektes (oder der Bibliothek).
     *
     * Der [dateipfad] muss dabei relativ zum `resources/` Ordner angegeben werden.
     */
    fun ladeTerritoriumAusProjekt(
        dateipfad: String,
        ausResourceOrdner: Boolean = false
    ): InternesTerritorium {
        val json =
            ResourceReader(getDateipfadMitSuffix(dateipfad), ausResourceOrdner).getInhaltAlsText()

        return ladeTerritoriumVonJSON(json)
    }

    /**
     * Lädt ein [InternesTerritorium] aus dem übergebenen [json].
     */
    private fun ladeTerritoriumVonJSON(json: String): InternesTerritorium {
        val startDaten = Json.decodeFromString<StartTerritoriumDaten>(json)

        return InternesTerritorium(
            hamster = startDaten.hamster.mapIndexed(::konvertiereHamsterDaten),
            kacheln = erstelleKachelMap(startDaten.kacheln),
            kachelZuMeterSkalierung = startDaten.kachelZuMeterSkalierung
        )
    }

    /**
     * Konvertiert die [HamsterDaten] zu einem [InternerHamster].
     */
    private fun konvertiereHamsterDaten(idx: Int, daten: HamsterDaten): InternerHamster =
        InternerHamster(
            position = daten.position,
            richtung = daten.richtung,
            inventar = daten.inventar,
            nummer = idx
        )

    /**
     * Erstellt aus dem [kachelDaten] die Map mit den [Kachel]n des Territoriums.
     */
    private fun erstelleKachelMap(kachelDaten: List<KachelDaten>): Map<Position, Kachel> {
        val kacheln = mutableMapOf<Position, Kachel>()
        kachelDaten.associateByTo(
            destination = kacheln,
            keySelector = { it.position },
            valueTransform = { Kachel(it.inhalt) }
        )

        return kacheln
    }

    /**
     * Fügt dem [dateipfad] die Endung ".json" an, sollte dieser nicht bereits damit enden.
     */
    private fun getDateipfadMitSuffix(dateipfad: String): String =
        if (dateipfad.endsWith(".json")) {
            dateipfad
        } else {
            "$dateipfad.json"
        }

}
