plugins {
    kotlin("jvm")

    id("org.jetbrains.compose")
}

dependencies {
    api(project(":core"))
    implementation(project(":ui"))

    implementation(compose.desktop.common)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}
