import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")

    id("org.jetbrains.compose")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":ui"))

    // Must be of a different type than the one in :ui because otherwise the dependencies will clash inside the IDE.
    compileOnly(compose.desktop.currentOs)
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "kotlin-hamster-editor"
            // Add a ".0" to the end, so it conforms with the MSI versioning scheme
            packageVersion = "${rootProject.version}.0"
        }
    }
}

tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }

tasks.test {
    useJUnit()
}
