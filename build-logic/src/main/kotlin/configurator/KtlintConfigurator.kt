package configurator

import com.android.build.gradle.BaseExtension
import helper.getVersion
import helper.libsVersionCatalog
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.jlleitschuh.gradle.ktlint.KtlintExtension

fun Project.configureKtlint() {
    configure<KtlintExtension> {
        version.set(libsVersionCatalog.getVersion("ktlint"))
        verbose.set(true)
        android.set(extensions.findByType<BaseExtension>() != null)
    }
}
