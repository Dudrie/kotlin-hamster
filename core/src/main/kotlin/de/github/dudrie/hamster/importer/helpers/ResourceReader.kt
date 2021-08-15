package de.github.dudrie.hamster.importer.helpers

import org.jetbrains.skija.Image
import java.io.FileNotFoundException
import java.net.URL

/**
 * Helper class to read contents from the resources.
 *
 * Supports reading resources either as text through [getContentAsText] or as [Image] through [getContentAsImage].
 *
 * @param filePath Path to the resource.
 *
 * @throws FileNotFoundException If the resource at the `filePath` could not be found.
 */
class ResourceReader(filePath: String) {
    private val resource: URL

    init {
        resource = this::class.java.getResource(filePath)
            ?: throw FileNotFoundException("The resource at \"$filePath\" could not be found.")
    }

    /**
     * Returns the content of the resource as [String].
     *
     * It uses UTF-8 as encoding to read the resource.
     *
     * @return Content of the resource as [String].
     */
    fun getContentAsText(): String = resource.readText(Charsets.UTF_8)

    /**
     * Returns the content of the resource as [Image].
     *
     * @return Content of the resource as [Image].
     */
    fun getContentAsImage(): Image = Image.makeFromEncoded(resource.readBytes())
}
