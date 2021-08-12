package de.gothub.dudrie.hamster.importer.helpers

import java.io.FileNotFoundException

class ResourceReader(filePath: String) {
    val content: String

    init {
        val resource = this::class.java.getResource(filePath)
            ?: throw FileNotFoundException("The resource at \"$filePath\" could not be found.")
        content = resource.readText(Charsets.UTF_8)
    }
}
