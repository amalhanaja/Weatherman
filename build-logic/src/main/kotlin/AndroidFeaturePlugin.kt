import helper.androidTestImplementation
import helper.api
import helper.applyPlugins
import helper.getLibraryProvider
import helper.getPluginId
import helper.implementation
import helper.kapt
import helper.libsVersionCatalog
import helper.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.applyPlugins(
            "weatherman.android.library.ui",
            libsVersionCatalog.getPluginId("kotlin.kapt"),
            libsVersionCatalog.getPluginId("hilt"),
        )
        dependencies {
            api(project(":core:designsystem"))
            api(project(":core:model"))
            api(project(":core:data"))
            implementation(libsVersionCatalog.getLibraryProvider("coroutine.core"))
            implementation(libsVersionCatalog.getLibraryProvider("hilt.android"))
            implementation(libsVersionCatalog.getLibraryProvider("hilt.compose.nav"))
            implementation(libsVersionCatalog.getLibraryProvider("lifecycle.viewmodel"))
            implementation(libsVersionCatalog.getLibraryProvider("lifecycle.compose"))
            kapt(libsVersionCatalog.getLibraryProvider("hilt.compiler"))
            implementation(libsVersionCatalog.getLibraryProvider("accompanist.navigation.animation"))

            testImplementation(libsVersionCatalog.getLibraryProvider("junit"))
            testImplementation(libsVersionCatalog.getLibraryProvider("coroutine.test"))
            testImplementation(libsVersionCatalog.getLibraryProvider("mockk"))
            testImplementation(libsVersionCatalog.getLibraryProvider("kotlin.test"))

            androidTestImplementation(libsVersionCatalog.getLibraryProvider("androidx.junit"))
            androidTestImplementation(libsVersionCatalog.getLibraryProvider("androidx.espresso.core"))
            androidTestImplementation(libsVersionCatalog.getLibraryProvider("compose.test.junit"))
        }
    }
}
