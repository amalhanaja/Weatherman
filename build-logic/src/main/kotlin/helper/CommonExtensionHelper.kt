package helper

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun CommonExtension<*, *, *, *>.kotlinOptions(action: KotlinJvmOptions.() -> Unit) {
    (this as? ExtensionAware)?.extensions?.configure("kotlinOptions", action)
}

internal fun Project.findCommonExtension(): CommonExtension<*, *, *, *>? {
    return extensions.findByType<LibraryExtension>() ?: extensions.findByType<ApplicationExtension>()
}

internal fun Project.findAndroidComponentExtension(): AndroidComponentsExtension<*, *, *>? {
    return extensions.findByType<ApplicationAndroidComponentsExtension>() ?: extensions.findByType<LibraryAndroidComponentsExtension>()
}
