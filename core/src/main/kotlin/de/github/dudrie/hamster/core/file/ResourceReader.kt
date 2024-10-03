package de.github.dudrie.hamster.core.file

import java.io.FileNotFoundException
import java.net.URL

/**
 * Liest die Resource im [dateiPfad] aus.
 *
 * Dabei muss sich die Resource entweder
 * 1. im `resource/` des aktuellen Projektes, welches diese Bibliothek verwendet, befinden
 * 2. ODER im `resource/` Ordner dieser Bibliothek.
 *
 * Ist [ausResourceOrdner] `true`, dann wird die Resource behandelt, wie in Fall 1 beschrieben, ansonsten wie in Fall 2.
 */
class ResourceReader(private val dateiPfad: String, private val ausResourceOrdner: Boolean) {

    /**
     * Gibt den Inhalt der Resource als [String] zurück.
     *
     * Die Resource muss dabei im [UTF-8 Format][Charsets.UTF_8] vorliegen.
     */
    fun getInhaltAlsText(): String = ladeResource().readText(Charsets.UTF_8)

    /**
     * Lädt die Resource aus dem [dateiPfad] in Abhängigkeit des Attributs [ausResourceOrdner].
     *
     * Gibt die Resource als [URL] zurück.
     *
     * @throws FileNotFoundException Es konnte beim [dateiPfad] keine Resource gefunden werden.
     */
    private fun ladeResource(): URL = if (ausResourceOrdner) {
        this::class.java.getResource(dateiPfad)
    } else {
        this::class.java.classLoader.getResource(dateiPfad)
    } ?: throw FileNotFoundException("Die Datei \"$dateiPfad\" konnte nicht gefunden werden.")

}
