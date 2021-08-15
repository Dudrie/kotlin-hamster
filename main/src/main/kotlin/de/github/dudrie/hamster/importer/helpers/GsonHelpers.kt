package de.github.dudrie.hamster.importer.helpers

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Parses the [json] into an instance of [T].
 */
inline fun <reified T> parseJson(json: String): T {
    val gson = Gson()
    return gson.fromJson(json, T::class.java)
}

/**
 * Returns the JSON string representation of the given [obj].
 */
fun <T> stringifyJson(obj: T): String {
    val gson = GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create()
    return gson.toJson(obj)
}
