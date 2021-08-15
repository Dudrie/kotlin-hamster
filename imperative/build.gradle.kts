plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":main"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}
