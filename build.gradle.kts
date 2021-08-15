import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val artifactPrefix = rootProject.name
group = "de.github.dudrie"
version = "1.0"

plugins {
    kotlin("jvm") version "1.5.21"

    id("org.jetbrains.compose") version "1.0.0-alpha1"
    id("org.jetbrains.dokka") version "1.5.0"
    id("maven-publish")
}

allprojects {
    group = "de.github.dudrie"
    version = "0.1"

    plugins.apply("maven-publish")
    plugins.apply("org.jetbrains.dokka")

    repositories {
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenCentral()
    }

    afterEvaluate {
        publishing {
            publications {
                create<MavenPublication>("maven") {
                    groupId = group.toString()
                    artifactId = "$artifactPrefix-${project.name}"
                    version = version

                    from(components["java"])
                }
            }
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    tasks.withType<DokkaTaskPartial>().configureEach() {
        dokkaSourceSets {
            configureEach {
                reportUndocumented.set(true)
                includeNonPublic.set(false)
            }
        }
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("kdoc"))
}
