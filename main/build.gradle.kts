plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":ui"))

    implementation("com.google.code.gson:gson:2.8.7")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}
