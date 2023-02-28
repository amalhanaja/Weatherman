@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("weatherman.android.library")
    id("weatherman.android.hilt")
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "dev.amalhanaja.weatherman.core.database"
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging3)
    ksp(libs.room.compiler)

    androidTestImplementation(libs.coroutine.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.kotlin.test)
    androidTestImplementation(libs.androidx.espresso.core)
}
