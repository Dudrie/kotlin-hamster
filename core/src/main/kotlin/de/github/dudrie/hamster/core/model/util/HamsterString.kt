package de.github.dudrie.hamster.core.model.util

/**
 * Eine Hilfsklasse, sodass die UI an entsprechenden Stellen die richtigen Texte anzeigt.
 *
 * @param id [HamsterStringId] des Textes.
 * @param args Weitere Argumente für den Text.
 */
class HamsterString(val id: HamsterStringId, vararg val args: Any)

/**
 * IDs für die unterschiedlichen [HamsterString]s.
 */
enum class HamsterStringId {
    /**  */
    KOMMANDO_HAMSTER_DREHE_LINKS,

    /**  */
    KOMMANDO_HAMSTER_LAUFE,

    /**  */
    KOMMANDO_HAMSTER_LEGE_KORN_AB,

    /**  */
    KOMMANDO_HAMSTER_SAGE,

    /**  */
    KOMMANDO_HAMSTER_SAMMLE_KORN_AUF,

    /**  */
    KOMMANDO_TERRITORIUM_SPAWNE_HAMSTER,

    /**  */
    ERR_NO_GRAIN_IN_MOUTH,

    /**  */
    ERR_NO_GRAIN_ON_TILE,

    /**  */
    ERR_POSITION_NOT_IN_TERRITORY,

    /**  */
    ERR_TILE_BLOCKED
}
