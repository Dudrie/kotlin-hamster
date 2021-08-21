plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":imperative"))

    compileOnly(project(":core"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}
