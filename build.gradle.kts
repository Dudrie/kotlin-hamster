import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileNotFoundException
import java.util.*

group = "de.github.dudrie"
version = getProjectVersion("1.0")

var gprUser: String? = null
var gprToken: String? = null

loadProperties()

plugins {
    kotlin("jvm") version "1.5.31"

    id("org.jetbrains.compose") version "1.0.0-beta5"
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
            withSourcesJar()
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        }
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

fun getProjectVersion(fallbackVersion: String): String {
    val version = project.findProperty("version") as String?

    return if (version == null || version == "unspecified") fallbackVersion else version
}

fun loadProperties() {
    val properties = Properties()
    try {
        properties.load(project.file("local.properties").inputStream())
    } catch (e: FileNotFoundException) {
        println("[INFO] local.properties not found. Falling back to environmental variables for publishing the produced artifacts.")
    }
    gprUser = properties["gpr.user"] as String? ?: System.getenv("GPR_ACTOR")
    gprToken = properties["gpr.token"] as String? ?: System.getenv("GPR_TOKEN")
}
