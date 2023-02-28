plugins {
    id("weatherman.android.feature")
}

android {
    namespace = "dev.amalhanaja.weatherman.feature.home"
}

dependencies {
    implementation(libs.kotlinx.datetime)
}
