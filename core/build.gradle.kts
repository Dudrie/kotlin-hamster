import org.jetbrains.compose.compose

plugins {
    kotlin("jvm")

    id("org.jetbrains.compose")
}

dependencies {
    implementation(compose.desktop.common)

    implementation("com.google.code.gson:gson:2.8.7")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}
