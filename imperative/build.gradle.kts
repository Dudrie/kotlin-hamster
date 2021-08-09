plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":ui"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}
