import configurator.configureJacoco
import helper.applyPlugins
import helper.findAndroidComponentExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.tasks.JacocoReport

class JacocoPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.applyPlugins("jacoco")

            tasks.withType<JacocoReport>().configureEach {
                dependsOn("test")
                configureJacoco(target, null)
            }
            findAndroidComponentExtension()?.onVariants { variant ->
                val unitTestTaskName = "test${variant.name.capitalized()}UnitTest"
                val androidTestCoverageReportTaskName = "create${variant.name.capitalized()}AndroidTestCoverageReport"

                tasks.register<JacocoReport>("jacoco${variant.name.capitalized()}Report") {
                    dependsOn(unitTestTaskName, androidTestCoverageReportTaskName)

                    group = "Reporting"
                    description = "Generate Jacoco coverage reports for the ${variant.name} build."

                    configureJacoco(target, variant.name)
                }
            }
        }
    }
}
