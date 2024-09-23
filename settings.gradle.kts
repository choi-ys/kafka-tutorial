rootProject.name = "kafka-tutorial"

registerModules(rootDir)

private fun registerModules(directory: File) {
    directory.walkTopDown()
        .filter { it.isDirectory && File(it, "build.gradle.kts").exists() && it != directory }
        .forEach { includeModule(it) }
}

private fun includeModule(moduleDir: File) {
    val relativePath = moduleDir.relativeTo(rootDir).path
    val moduleName = convertPathToModuleName(relativePath)

    include(moduleName)
}

private fun convertPathToModuleName(relativePath: String): String {
    val modulePrefix = ":"
    val moduleName = relativePath.replace(File.separatorChar, modulePrefix.first())
    return "$modulePrefix$moduleName"
}
