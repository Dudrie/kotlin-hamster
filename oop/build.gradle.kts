plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":core"))
    implementation(project(":ui"))

    implementation(libs.kotlinx.coroutines)
}
