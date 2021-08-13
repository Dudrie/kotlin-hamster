package de.github.dudrie.hamster.importer.helpers

import org.jetbrains.skija.Image
import java.io.FileNotFoundException
import java.net.URL

class ResourceReader(filePath: String) {
    private val resource: URL

    init {
        resource = this::class.java.getResource(filePath)
            ?: throw FileNotFoundException("The resource at \"$filePath\" could not be found.")
    }

    fun getContentAsText(): String = resource.readText(Charsets.UTF_8)

    fun getContentAsImage(): Image = Image.makeFromEncoded(resource.readBytes())
}
