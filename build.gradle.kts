import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "de.github.dudrie"
version = "1.0"

plugins {
    kotlin("jvm") version "1.5.21"

    id("org.jetbrains.compose") version "1.0.0-alpha1"
    id("org.jetbrains.dokka") version "1.5.0"
}

allprojects {
    group = "de.github.dudrie"
    version = "0.1"

    plugins.apply("org.jetbrains.dokka")

    repositories {
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenCentral()

    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("kdoc"))
}
