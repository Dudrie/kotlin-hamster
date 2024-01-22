plugins {
    kotlin("jvm")

    id("org.jetbrains.compose")
}

val gsonVersion: String by project

dependencies {
    implementation(compose.desktop.common)

    implementation("com.google.code.gson:gson:$gsonVersion")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}
