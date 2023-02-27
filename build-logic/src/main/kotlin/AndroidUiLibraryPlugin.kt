import com.android.build.api.dsl.LibraryExtension
import configurator.configureCompose
import configurator.configureGradleManagedDevice
import helper.applyPlugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidUiLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.applyPlugins("weatherman.android.library")
        extensions.configure<LibraryExtension> {
            configureCompose(this)
            configureGradleManagedDevice(this)
        }
    }
}
