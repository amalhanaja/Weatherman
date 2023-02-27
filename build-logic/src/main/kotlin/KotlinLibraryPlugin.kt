import configurator.configureKotlin
import helper.applyPlugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.applyPlugins(
                "kotlin",
                "weatherman.jacoco",
                "weatherman.ktlint",
            )
            configureKotlin()
        }
    }
}
