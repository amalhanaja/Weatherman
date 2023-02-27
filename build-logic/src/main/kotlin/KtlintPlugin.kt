import configurator.configureKtlint
import helper.applyPlugins
import helper.getPluginId
import helper.libsVersionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project

class KtlintPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.applyPlugins(libsVersionCatalog.getPluginId("ktlint"))
            configureKtlint()
        }
    }
}
