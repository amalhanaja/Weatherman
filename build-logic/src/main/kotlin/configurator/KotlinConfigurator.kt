package configurator

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>() {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()

            // Treat all Kotlin warnings as errors
            allWarningsAsErrors = false

            // Compiler Arguments
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                // Enable experimental coroutines APIs, including Flow
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                "-opt-in=kotlin.Experimental",

                // Enable compose experimental APIs, including material and animation
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",

                // Enable paging experimental APIs
                "-opt-in=androidx.paging.ExperimentalPagingApi",
            )

        }
    }
}
