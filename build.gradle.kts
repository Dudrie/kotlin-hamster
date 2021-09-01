import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileNotFoundException
import java.util.*

val jvmTarget = "11"
group = "de.github.dudrie"
version = "0.1"

var gprUser: String? = null
var gprToken: String? = null

try {
    val properties = Properties()
    properties.load(project.file("local.properties").inputStream())
    gprUser = properties["gpr.user"] as String?
    gprToken = properties["gpr.token"] as String?
} catch (e: FileNotFoundException) {
    println("[INFO] local.properties file is missing. Make sure to add one if you want to publish the artifacts.")
}

plugins {
    kotlin("jvm") version "1.5.21"

    id("org.jetbrains.compose") version "1.0.0-alpha3"
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

    afterEvaluate {
        java {
            withJavadocJar()
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = jvmTarget
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
                artifactId = "${rootProject.name}-${project.name}"
                version = version

                afterEvaluate {
                    from(components["java"])
                }
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/Dudrie/kotlin-hamster")
                credentials {
                    username = gprUser
                    password = gprToken
                }
            }
        }
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("kdoc"))
}
