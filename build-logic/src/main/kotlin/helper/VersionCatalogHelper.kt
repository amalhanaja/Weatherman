package helper

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.VersionConstraint
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import java.util.Optional

val Project.libsVersionCatalog: VersionCatalog get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

val Optional<VersionConstraint>.asString: String get() = get().toString()

val Optional<VersionConstraint>.asInt: Int get() = asString.toInt()

fun VersionCatalog.getPluginId(pluginAlias: String): String {
    return this.findPlugin(pluginAlias).get().get().pluginId
}

fun VersionCatalog.getVersion(versionAlias: String): String {
    return findVersion(versionAlias).get().toString()
}

fun VersionCatalog.getLibraryProvider(libraryAlias: String): Provider<MinimalExternalModuleDependency> {
    return findLibrary(libraryAlias).get()
}
