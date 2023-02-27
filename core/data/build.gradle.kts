plugins {
    id("weatherman.android.library")
}

android {
    namespace = "dev.amalhanaja.weatherman.core.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.coroutine.core)
}
