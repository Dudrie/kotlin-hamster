plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":ui"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}
