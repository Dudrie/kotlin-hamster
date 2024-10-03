import org.jetbrains.dokka.gradle.DokkaTaskPartial

plugins {
    kotlin("jvm")

    alias(libs.plugins.compose.core)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    implementation(project(":core"))
    implementation(project(":ui"))

    implementation(compose.desktop.currentOs) {
        exclude("org.jetbrains.compose.material")
    }

    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation(compose.components.resources)

    implementation(libs.compose.viewmodel)

    implementation(libs.kotlinx.coroutines)

    implementation(libs.dudrie.commons.forms)
    implementation(libs.dudrie.commons.select)
}

compose.resources {
    packageOfResClass = "de.github.dudrie.hamster.editor.generated"
    generateResClass = always
}

tasks.withType<DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        configureEach {
            reportUndocumented.set(false)
            includeNonPublic.set(false)
        }
    }
}

tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
