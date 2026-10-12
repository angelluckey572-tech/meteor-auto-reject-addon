plugins {
    id("fabric-loom") version "1.10.2"
    id("java")
}

val mavenGroup = providers.gradleProperty("maven_group").orElse("com.example").get()
val archivesBaseName = providers.gradleProperty("archives_base_name").orElse("autoreject").get()

group = mavenGroup
version = "1.0.0"

base {
    archivesName.set(archivesBaseName)
}

repositories {
    mavenCentral()
    maven("https://maven.meteordev.org/releases")
    maven("https://maven.meteordev.org/snapshots")
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.10")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:0.16.7")
    modImplementation("meteordevelopment:meteor-client:1.21.10-SNAPSHOT")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.jar {
    archiveVersion.set("")
}
