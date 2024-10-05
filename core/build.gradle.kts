plugins {
    kotlin("jvm")

    kotlin("plugin.serialization") version libs.versions.kotlin
}

dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization)
}
