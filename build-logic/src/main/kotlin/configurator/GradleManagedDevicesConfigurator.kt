package configurator

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ManagedVirtualDevice

@Suppress("UnstableApiUsage")
internal fun configureGradleManagedDevice(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    val deviceConfigs = listOf(
        DeviceConfig("Pixel 2", 31, "google")
    )

    commonExtension.testOptions {
        deviceConfigs.forEach { deviceConfig ->
            managedDevices.devices.maybeCreate(deviceConfig.taskName, ManagedVirtualDevice::class.java).apply {
                device = deviceConfig.device
                apiLevel = deviceConfig.apiLevel
                systemImageSource = deviceConfig.systemSourceImage
            }
        }
    }
}

data class DeviceConfig(
    val device: String,
    val apiLevel: Int,
    val systemSourceImage: String,
) {
    val taskName = buildString {
        append(device.lowercase().replace(" ", ""))
        append("api$apiLevel")
        append(systemSourceImage.replace("-", ""))
    }
}
