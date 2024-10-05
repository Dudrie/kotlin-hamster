package de.github.dudrie.hamster.core.game

/**
 * Beschreibt die unterschiedlichen Modi, in denen sich das Spiel befinden kann.
 */
enum class SpielModus {
    /**
     * Das Spiel wird initialisiert und bspw. gerade geladen.
     *
     * Es können keine Kommandos ausgeführt werden.
     */
    Initialisierung,

    /**
     * Das Spiel läuft.
     *
     * Das ist der einzige Zustand, in dem Kommandos ausgeführt werden können.
     */
    Lauft,

    /**
     * Das Spiel ist pausiert, kann aber fortgesetzt werden.
     *
     * Es können keine Kommandos ausgeführt werden.
     */
    Pausiert,

    /**
     * Das Spiel wurde abgebrochen, bevor es zu Ende war, bspw. durch einen aufgetretenen Fehler.
     *
     * Es können keine Kommandos ausgeführt werden.
     */
    Abgebrochen,

    /**
     * Das Spiel wurde gestoppt.
     *
     * Es können keine Kommandos mehr ausgeführt werden.
     */
    Gestoppt
}
