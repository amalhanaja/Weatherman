@file:Suppress("UnstableApiUsage")

package configurator

import com.android.build.api.dsl.CommonExtension
import helper.androidTestImplementation
import helper.api
import helper.debugImplementation
import helper.getLibraryProvider
import helper.getVersion
import helper.implementation
import helper.kotlinOptions
import helper.libsVersionCatalog
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import java.io.File

internal fun Project.configureCompose(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    with(commonExtension) {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libsVersionCatalog.getVersion("compose.compiler")
        }

        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + generateComposeMetricsParameter()
        }

        dependencies {
            implementation(libsVersionCatalog.getLibraryProvider("androidx.core"))
            api(libsVersionCatalog.getLibraryProvider("compose.ui"))
            api(libsVersionCatalog.getLibraryProvider("compose.preview"))
            api(libsVersionCatalog.getLibraryProvider("compose.material3"))
            api(libsVersionCatalog.getLibraryProvider("compose.material"))
            debugImplementation(libsVersionCatalog.getLibraryProvider("compose.tooling"))
            debugImplementation(libsVersionCatalog.getLibraryProvider("compose.test.manifest"))

            androidTestImplementation(libsVersionCatalog.getLibraryProvider("androidx.junit"))
            androidTestImplementation(libsVersionCatalog.getLibraryProvider("androidx.espresso.core"))
            androidTestImplementation(libsVersionCatalog.getLibraryProvider("compose.test.junit"))
        }
    }
}

private fun Project.generateComposeMetricsParameter(): List<String> {
    val metricParameters = mutableListOf<String>()
    val isEnableMetricsProvider = project.providers.gradleProperty("enableComposeCompilerMetrics")
    val isEnableMetrics = isEnableMetricsProvider.orNull == "true"
    if (isEnableMetrics) {
        val metricsFolder = File(buildDir, "compose-metrics")
        metricParameters.add("-P")
        metricParameters.add(
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + metricsFolder.absolutePath
        )
    }

    val enableReportsProvider = project.providers.gradleProperty("enableComposeCompilerReports")
    val enableReports = (enableReportsProvider.orNull == "true")
    if (enableReports) {
        val reportsFolder = File(project.buildDir, "compose-reports")
        metricParameters.add("-P")
        metricParameters.add(
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + reportsFolder.absolutePath
        )
    }
    return metricParameters.toList()
}
