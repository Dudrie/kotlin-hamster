package de.github.dudrie.hamster.oop.model

/**
 * Grundklasse, um ein Spiel zu laden und zu starten.
 *
 * @param territoriumsDatei Pfad zur Territoriumsdatei, die geladen werden soll. Es wird ein Standardterritorium geladen, wenn weggelassen.
 */
abstract class EinfachesHamsterSpiel(private val territoriumsDatei: String? = null) :
    HamsterSpielOhnePaule(territoriumsDatei) {

    /**
     * Lädt das Spiel mit dem gegebenen [spielName] und der entsprechenden [spielNr].
     *
     * Das Territorium muss sich im `/territories` Ordner befinden.
     *
     * @param spielName Name des Hamsterspiels, welches geladen werden soll.
     * @param spielNr Nummer des Spiels.
     */
    constructor(spielName: String, spielNr: Int) : this("territories/${spielName}_$spielNr.json")

    /**
     * Enthält den Standardhamster, der "paule" genannt wird.
     */
    var paule: Hamster
        private set

    init {
        paule = Hamster(territorium, spiel.standardHamster)
    }

}
