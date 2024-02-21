pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "kotlin-hamster"

include("core", "main", "ui", "imperative", "editor", "imperative-de", "presentation")
