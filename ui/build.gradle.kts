import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")

    alias(libs.plugins.compose.core)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    implementation(project(":core"))

    implementation(compose.desktop.currentOs) {
        exclude("org.jetbrains.compose.material")
    }

    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation(compose.components.resources)

    implementation(libs.kotlinx.coroutines)
}

compose.resources {
    packageOfResClass = "de.github.dudrie.hamster.ui.generated"
    generateResClass = always
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "kotlin-hamster"
            packageVersion = "${rootProject.version}"
        }
    }
}

tasks.test {
    useJUnit()
}
