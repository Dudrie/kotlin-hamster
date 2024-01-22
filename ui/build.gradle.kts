import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")

    id("org.jetbrains.compose")
}

val coroutinesVersion: String by project

dependencies {
    implementation(project(":core"))

    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    testImplementation(kotlin("test"))
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "kotlin-hamster"
            packageVersion = "${rootProject.version}.0"
        }
    }
}

tasks.test {
    useJUnit()
}
