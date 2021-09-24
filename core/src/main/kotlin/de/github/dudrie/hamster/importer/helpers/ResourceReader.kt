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
 * @param fromOuterModule If `true` the file gets loading from outside the module. Defaults to `false`.
 *
 * @throws FileNotFoundException If the resource at the `filePath` could not be found.
 */
class ResourceReader(private val filePath: String, private val fromOuterModule: Boolean = false) {
    private val resource: URL

    init {
        resource = this.loadResource()
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

    private fun loadResource(): URL {
        val url = if (fromOuterModule) {
            this::class.java.classLoader.getResource(filePath)
        } else {
            this::class.java.getResource(filePath)
        }

        return url ?: throw FileNotFoundException("The resource at \"$filePath\" could not be found.")
    }
}
