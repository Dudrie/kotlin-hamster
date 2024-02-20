package de.github.dudrie.hamster.external.model

/**
 * Abstraktion eines einfachen Hamsterspiels.
 */
abstract class EinfachesHamsterSpiel(territoryFile: String? = null) {

    /**
     * Enthält den Standardhamster, der "paule" genannt wird.
     */
    protected val paule: Hamster

    /**
     * Tatsächliches [HamsterSpiel].
     */
    private val spiel: HamsterSpiel = HamsterSpiel(territoryFile)

    /**
     * Erstellt ein [HamsterSpiel], welches die Territoriumsdatei nach folgendem Muster benutzt:
     *
     * `${spielName}_${spielNummer}.json`
     *
     * @throws java.io.FileNotFoundException Die Datei konnte nicht gefunden werden.
     */
    constructor(spielName: String, spielNummer: Int) : this("territories/${spielName}_$spielNummer.json")

    init {
        spiel.starteSpiel()

        paule = spiel.paule
    }

    /**
     * Methode, um das Spielverhalten zu implementieren.
     *
     * Ist so designt, dass sie von Unterklassen überschrieben werden kann.
     *
     * In diese Methode gehört der Hamstercode.
     */
    protected abstract fun fuehreAus()

    /**
     * Führt das Spiel, welches in der [fuehreAus] Methode implementiert wurde, tatsächlich aus.
     */
    fun lasseSpielLaufen() {
        try {
            fuehreAus()
        } catch (e: RuntimeException) {
            throw e
        }

        spiel.stoppeSpiel()
    }

}
