package helper

import org.gradle.api.plugins.PluginManager

fun PluginManager.applyPlugins(vararg pluginIds: String) {
    pluginIds.forEach(::apply)
}
