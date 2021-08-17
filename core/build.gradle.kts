import org.jetbrains.compose.compose

plugins {
    kotlin("jvm")

    id("org.jetbrains.compose")
}

dependencies {
    implementation(compose.desktop.currentOs)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}
