import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val artifactPrefix = rootProject.name
group = "de.github.dudrie"
version = "0.1"

plugins {
    kotlin("jvm") version "1.5.21"

    id("org.jetbrains.compose") version "1.0.0-alpha1"
    id("org.jetbrains.dokka") version "1.5.0"

    `maven-publish`
}

allprojects {
    group = rootProject.group
    version = rootProject.version

    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.dokka")

    repositories {
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    tasks.withType<DokkaTaskPartial>().configureEach {
        dokkaSourceSets {
            configureEach {
                reportUndocumented.set(true)
                includeNonPublic.set(false)
            }
        }
    }
}

subprojects {
    publishing {
        publications {
            register<MavenPublication>("gpr") {
                groupId = group.toString()
                artifactId = "$artifactPrefix-${project.name}"
                version = version

                afterEvaluate {
                    from(components["java"])
                }
            }
        }
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("kdoc"))
}
