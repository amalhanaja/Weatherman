plugins {
    id("weatherman.android.library")
    id("weatherman.android.hilt")
}

android {
    namespace = "dev.amalhanaja.weatherman.core.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:network"))
    implementation(libs.coroutine.core)
    implementation(libs.kotlinx.datetime)
}
