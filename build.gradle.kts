import org.jetbrains.dokka.gradle.DokkaTaskPartial
import java.io.FileNotFoundException
import java.util.*

group = "de.github.dudrie"
version = getProjectVersion("3.0.0")

var mavenUser: String? = null
var mavenPass: String? = null

loadProperties()

plugins {
    kotlin("jvm") version libs.versions.kotlin

    alias(libs.plugins.dokka)
    alias(libs.plugins.versions)

    `maven-publish`
}

allprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenCentral()
    }

    afterEvaluate {
        kotlin {
            jvmToolchain(21)
        }

        java {
            withJavadocJar()
            withSourcesJar()
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
    apply(plugin = "maven-publish")
    apply(plugin = rootProject.project.libs.plugins.dokka.get().pluginId)

    publishing {
        publications {
            register<MavenPublication>("maven") {
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
                name = "Reposilite"
                url = uri("https://maven.dudrie.de/releases")
                credentials {
                    username = mavenUser
                    password = mavenPass
                }
            }
        }
    }
}

tasks.register("generateResources") {
    dependsOn(
        "ui:clean",
        "ui:generateResourceAccessorsForMain",
        "ui:generateComposeResClass"
    )
    dependsOn(
        "editor:clean",
        "editor:generateResourceAccessorsForMain",
        "editor:generateComposeResClass"
    )
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(layout.buildDirectory.dir("kdoc"))
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
    mavenUser = properties["maven.user"] as String? ?: System.getenv("MAVEN_USER")
    mavenPass = properties["maven.password"] as String? ?: System.getenv("MAVEN_PASSWORD")
}
