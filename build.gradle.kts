group = "de.github.dudrie"
version = "1.0"

plugins {
    val kotlinVersion: String by System.getProperties()
    kotlin("jvm") version kotlinVersion
}

allprojects {
    group = "de.github.dudrie"
    version = "0.1"

    repositories {
        mavenCentral()
    }
}