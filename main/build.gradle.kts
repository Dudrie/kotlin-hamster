import org.jetbrains.compose.compose

plugins {
    kotlin("jvm")

    id("org.jetbrains.compose")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":ui"))

    implementation(compose.desktop.common)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}
