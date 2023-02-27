package configurator

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import helper.asInt
import helper.getVersion
import helper.libsVersionCatalog
import org.gradle.api.JavaVersion
import org.gradle.api.Project

@Suppress("UnstableApiUsage")
fun Project.configureAndroid(commonExtension: CommonExtension<*, *, *, *>) {
    with(commonExtension) {
        compileSdk = libsVersionCatalog.getVersion("compileSdk").toInt()
        defaultConfig {
            minSdk = libsVersionCatalog.getVersion("minSdk").toInt()
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
        buildTypes.all {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
        (this as? ApplicationExtension)?.defaultConfig?.targetSdk = libsVersionCatalog.findVersion("targetSdk").asInt
    }
}
