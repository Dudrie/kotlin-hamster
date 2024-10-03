package de.github.dudrie.hamster.core.file

import de.github.dudrie.hamster.core.file.model.HamsterDaten
import de.github.dudrie.hamster.core.file.model.KachelDaten
import de.github.dudrie.hamster.core.file.model.StartTerritoriumDaten
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.Kachel
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.Position
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Files
import kotlin.io.path.Path

object SpielExporter {

    private val format = Json { prettyPrint = false }

    fun speichereSpiel(dateipfad: String, territorium: InternesTerritorium) {
        val daten = StartTerritoriumDaten(
            kacheln = konvertiereKacheln(territorium.kacheln),
            hamster = territorium.hamster.map { konvertiereHamster(it) },
            kachelZuMeterSkalierung = territorium.kachelZuMeterSkalierung
        )
        val datei = if (dateipfad.endsWith(".json")) {
            Path(dateipfad)
        } else {
            Path("$dateipfad.json")
        }

        Files.newBufferedWriter(datei, Charsets.UTF_8)
            .use { it.write(format.encodeToString(daten)) }
    }

    private fun konvertiereHamster(hamster: InternerHamster): HamsterDaten = HamsterDaten(
        position = hamster.position,
        richtung = hamster.richtung,
        inventar = hamster.inventar.toMutableList() // Make a copy
    )

    private fun konvertiereKacheln(kacheln: Map<Position, Kachel>): List<KachelDaten> =
        kacheln.map { (pos, kachel) ->
            KachelDaten(position = pos, inhalt = kachel.inhalt)
        }
}
