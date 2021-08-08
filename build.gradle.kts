import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "de.github.dudrie"
version = "1.0"

plugins {
    kotlin("jvm") version "1.5.21"
}

allprojects {
    group = "de.github.dudrie"
    version = "0.1"

    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}