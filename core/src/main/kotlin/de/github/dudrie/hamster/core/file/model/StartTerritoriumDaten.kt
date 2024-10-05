package de.github.dudrie.hamster.core.file.model

import kotlinx.serialization.Serializable

/**
 * Daten eines [de.github.dudrie.hamster.core.model.territory.InternesTerritorium] zur Speicherung in einer Datei.
 *
 * @param kacheln [KachelDaten] aller Kacheln dieses Territoriums.
 * @param hamster [HamsterDaten] aller Hamster dieses Territoriums.
 * @param kachelZuMeterSkalierung Wert in Metern, den eine Kachel im Territorium abbildet.
 */
@Serializable
class StartTerritoriumDaten(
    val kacheln: List<KachelDaten>,
    val hamster: List<HamsterDaten>,
    val kachelZuMeterSkalierung: Double
)
