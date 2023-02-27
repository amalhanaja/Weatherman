import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import configurator.configureJacoco
import helper.applyPlugins
import helper.getVersion
import helper.libsVersionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

class JacocoMergeAllReportPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.applyPlugins("jacoco")
        configure<JacocoPluginExtension> {
            toolVersion = libsVersionCatalog.getVersion("jacoco")
        }

        afterEvaluate {
            val rootProject = this
            val rootSubProjects = subprojects
            rootSubProjects
                .first { it.name == "app" }
                .afterEvaluate {
                    extensions.getByType<ApplicationAndroidComponentsExtension>().onVariants {
                        val variantName = it.name
                        rootProject.tasks.create<JacocoReport>("jacocoMergeAll${variantName.capitalized()}Reports") {
                            group = "Reporting"
                            description = "Generate overall Jacoco coverage report for the $variantName build."

                            configureJacoco(rootProject, variantName)
                        }
                    }
                }
        }

    }
}
