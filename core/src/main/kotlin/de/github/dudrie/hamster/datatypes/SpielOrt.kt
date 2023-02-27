package de.github.dudrie.hamster.datatypes

/**
 * Repräsentiert einen Ort in der Spielwelt.
 *
 * @param spalte Spaltenindex des Ortes (beginnt bei 0). Muss 0 oder größer sein.
 * @param zeile Zeilenindex des Ortes (beginnt bei 0). Muss 0 oder größer sein.
 */
data class SpielOrt(
    /**
     * Spaltenindex des Ortes (beginnt bei 0). Muss 0 oder größer sein.
     */
    val spalte: Int,

    /**
     * Zeilenindex des Ortes (beginnt bei 0). Muss 0 oder größer sein.
     */
    val zeile: Int
) {

    init {
        require(spalte >= 0) { "Spalte muss 0 oder größer sein." }
        require(zeile >= 0) { "Zeile muss 0 oder größer sein." }
    }

    /**
     * Creates a new [SpielOrt] by translating this [SpielOrt] with the given [LocationVector].
     *
     * @param vector [LocationVector] used for translation.
     *
     * @return New [SpielOrt] translated by the given [vector].
     */
    fun translate(vector: LocationVector): SpielOrt =
        SpielOrt(spalte = spalte + vector.columnDelta, zeile = zeile + vector.rowDelta)

    /**
     * @return String representation of this [SpielOrt].
     */
    override fun toString(): String {
        return "(col: $spalte, row: $zeile)"
    }
}
