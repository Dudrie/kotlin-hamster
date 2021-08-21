plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":imperative"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}
