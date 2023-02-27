import configurator.configureAndroid
import configurator.configureGradleManagedDevice
import configurator.configureKotlin
import helper.applyPlugins
import helper.findCommonExtension
import helper.getLibraryProvider
import helper.getPluginId
import helper.implementation
import helper.libsVersionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.applyPlugins(
                libsVersionCatalog.getPluginId("android.application"),
                libsVersionCatalog.getPluginId("kotlin.android"),
                "weatherman.ktlint",
            )

            findCommonExtension()?.apply {
                configureAndroid(this)
                configureGradleManagedDevice(this)
            }
            configureKotlin()

            dependencies {
                implementation(libsVersionCatalog.getLibraryProvider("accompanist.navigation.animation"))
            }
        }
    }
}
