package de.github.dudrie.hamster.importer.helpers

import com.google.gson.Gson
import com.google.gson.GsonBuilder

inline fun <reified T> parseJson(json: String): T {
    val gson = Gson()
    return gson.fromJson(json, T::class.java)
}

fun <T> stringifyJson(obj: T): String {
    val gson = GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create()
    return gson.toJson(obj)
}
