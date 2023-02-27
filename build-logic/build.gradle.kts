plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.gradle.plugin.android)
    compileOnly(libs.gradle.plugin.kotlin)
    compileOnly(libs.gradle.plugin.ktlint)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "weatherman.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("androidHilt") {
            id = "weatherman.android.hilt"
            implementationClass = "AndroidHiltPlugin"
        }
        register("kotlinLibrary") {
            id = "weatherman.kotlin.library"
            implementationClass = "KotlinLibraryPlugin"
        }
        register("androidLibrary") {
            id = "weatherman.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("androidUiLibrary") {
            id = "weatherman.android.library.ui"
            implementationClass = "AndroidUiLibraryPlugin"
        }
        register("androidFeatureLibrary") {
            id = "weatherman.android.feature"
            implementationClass = "AndroidFeaturePlugin"
        }
        register("jacoco") {
            id = "weatherman.jacoco"
            implementationClass = "JacocoPlugin"
        }
        register("jacocoMergeReports") {
            id = "weatherman.jacoco.merge.report"
            implementationClass = "JacocoMergeAllReportPlugin"
        }
        register("ktlint") {
            id = "weatherman.ktlint"
            implementationClass = "KtlintPlugin"
        }
    }
}
