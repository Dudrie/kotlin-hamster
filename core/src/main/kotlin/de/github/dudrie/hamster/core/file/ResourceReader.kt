package de.github.dudrie.hamster.core.file

import java.io.FileNotFoundException
import java.net.URL

class ResourceReader(private val dateiPfad: String, private val ausResourceOrdner: Boolean) {

    fun getInhaltAlsText(): String = ladeResource().readText(Charsets.UTF_8)

    private fun ladeResource(): URL = if (ausResourceOrdner) {
        this::class.java.getResource(dateiPfad)
    } else {
        this::class.java.classLoader.getResource(dateiPfad)
    } ?: throw FileNotFoundException("Die Datei \"$dateiPfad\" konnte nicht gefunden werden.")
}
