import net.fabricmc.loom.util.Constants

plugins {
    java
    `java-library`
    `kotlin-dsl`
    `maven-publish`
    id("com.diffplug.spotless") version "5.8.2"
    id("fabric-loom") version "0.5-SNAPSHOT"
}

val archivesBaseName: String by project
base.archivesBaseName = archivesBaseName

val mavenGroup: String by project
group = mavenGroup

val baseVersion: String by project
val minecraftVersion: String by project
version = "$baseVersion+$minecraftVersion"

val yarnBuild: String by project
val loaderVersion: String by project
val fabricApiVersion: String by project

val ccaVersion: String by project

// For Removal
@get:kotlin.Deprecated("For removal")
val spinneryVersion: String by project

repositories {
    maven("https://dl.bintray.com/ladysnake/libs") {
        name = "Ladysnake Libs"
    }

    maven("https://dl.bintray.com/spinnery/Spinnery") {
        name = "Spinnery"
    }
}

val modImplementationAndInclude by configurations.register("modImplementationAndInclude")

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$minecraftVersion+build.$yarnBuild:v2")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")

    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")
    modImplementationAndInclude("io.github.onyxstudios.Cardinal-Components-API:cardinal-components-base:$ccaVersion")
    modImplementationAndInclude("io.github.onyxstudios.Cardinal-Components-API:cardinal-components-entity:$ccaVersion")

    modImplementationAndInclude("com.github.vini2003:spinnery:$spinneryVersion")

    add(sourceSets.main.get().getTaskName("mod", JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME), modImplementationAndInclude)
    add(Constants.Configurations.INCLUDE, modImplementationAndInclude)
}

tasks.processResources {
    inputs.properties("version" to project.version)

    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withSourcesJar()
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${project.base.archivesBaseName}"}
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"

    // The Minecraft launcher currently installs Java 8 for users, so your mod probably wants to target Java 8 too
    // JDK 9 introduced a new way of specifying this that will make sure no newer classes or methods are used.
    // We'll use that if it's available, but otherwise we'll use the older option.
    val targetVersion = 8

    if (JavaVersion.current().isJava9Compatible) {
        options.release.set(targetVersion)
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifact(tasks.remapJar) {
                builtBy(tasks.remapJar)
            }

            artifact(tasks.remapSourcesJar) {
                builtBy(tasks.remapSourcesJar)
            }
        }

        repositories {
        }
    }
}
