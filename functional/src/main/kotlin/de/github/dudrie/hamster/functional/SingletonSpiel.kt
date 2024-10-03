package de.github.dudrie.hamster.functional

import de.github.dudrie.hamster.oop.model.EinfachesHamsterSpiel
import de.github.dudrie.hamster.oop.model.Hamster
import de.github.dudrie.hamster.oop.model.Territorium

/**
 * Stellt die Verbindungsstelle zwischen dem OOP Modul und dem funktionalen Modul dar.
 */
internal class SingletonSpiel private constructor() {

    companion object {
        private var _spiel: EinfachesHamsterSpiel? = null

        /**
         * Das aktuell geladene [EinfachesHamsterSpiel].
         *
         * @throws IllegalStateException Wenn kein Spiel [geladen][ladeSpiel] wurde.
         */
        val spiel: EinfachesHamsterSpiel
            get() = _spiel ?: throw IllegalStateException("Es wurde noch kein Spiel gestartet.")

        /**
         * Einfacher Zugriff auf den [Hamster] des geladenen [spiel]s.
         */
        val paule: Hamster
            get() = spiel.paule

        /**
         * Einfacher Zugriff auf das [Territorium] des geladenen [Spiel]s.
         */
        val territorium: Territorium
            get() = spiel.territorium

        /**
         * Lädt ein [EinfachesHamsterSpiel] mit dem Territorium am angegebenen [dateipfand].
         *
         * @throws IllegalArgumentException Wenn bereits ein anderes Spiel geladen wurde.
         */
        fun ladeSpiel(dateipfand: String?) {
            require(_spiel == null) { "Es darf immer nur ein Hamsterspiel gleichzeitig gestartet sein." }
            _spiel = EinfachesHamsterSpiel(dateipfand)
            spiel.starteSpiel()
        }

        /**
         * Lädt ein [EinfachesHamsterSpiel] mit dem Territorium am angegebenen [dateipfand] und [pausiert][de.github.dudrie.hamster.core.game.SpielModus.Pausiert] es direkt.
         *
         * @throws IllegalArgumentException Wenn bereits ein anderes Spiel geladen wurde.
         *
         * @see ladeSpiel
         */
        fun ladeSpielPausiert(dateipfand: String?) {
            require(_spiel == null) { "Es darf immer nur ein Hamsterspiel gleichzeitig gestartet sein." }
            _spiel = EinfachesHamsterSpiel(dateipfand)
            spiel.starteSpielPausiert()
        }
    }
}
