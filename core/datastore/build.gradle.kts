plugins {
    id("weatherman.android.library")
    id("weatherman.android.hilt")
}

android {
    namespace = "dev.amalhanaja.weatherman.core.datastore"
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.datastore.preferences)

    testImplementation(libs.mockk)
    testImplementation(libs.junit)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.kotlin.test)
}
