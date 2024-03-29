plugins {
    kotlin("jvm")

    id("org.jetbrains.compose")
}

val coroutinesVersion: String by project

dependencies {
    api(project(":main"))
    api(project(":ui"))

    compileOnly(compose.desktop.currentOs)
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}

//tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
//tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
