import helper.applyPlugins
import helper.getLibraryProvider
import helper.getPluginId
import helper.implementation
import helper.kapt
import helper.libsVersionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.applyPlugins(
                libsVersionCatalog.getPluginId("hilt"),
                libsVersionCatalog.getPluginId("kotlin.kapt"),
            )
            dependencies {
                implementation(libsVersionCatalog.getLibraryProvider("hilt.android"))
                kapt(libsVersionCatalog.getLibraryProvider("hilt.compiler"))
            }
        }
    }
}
