import configurator.configureAndroid
import configurator.configureKotlin
import helper.applyPlugins
import helper.findCommonExtension
import helper.getPluginId
import helper.libsVersionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.applyPlugins(
            libsVersionCatalog.getPluginId("android.library"),
            libsVersionCatalog.getPluginId("kotlin.android"),
            "weatherman.jacoco",
            "weatherman.ktlint",
        )
        findCommonExtension()?.apply {
            configureAndroid(this)
        }
        configureKotlin()
    }
}
