@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.version.toml"))
        }
    }
}

rootProject.name = "build-logic"
