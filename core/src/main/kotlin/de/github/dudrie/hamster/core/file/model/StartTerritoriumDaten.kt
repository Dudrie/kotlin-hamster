package de.github.dudrie.hamster.core.file.model

import kotlinx.serialization.Serializable

@Serializable
class StartTerritoriumDaten(
    val kacheln: List<KachelDaten>,
    val startHamster: StartHamsterDaten?,
    val kachelZuMeterSkalierung: Double
)
