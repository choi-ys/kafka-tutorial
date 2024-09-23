rootProject.name = "kafka-tutorial"

registerModules(rootDir)

private fun registerModules(directory: File) = directory.walkTopDown()
    .filter { isNotRootModule(it, directory) && it.isDirectory && existBuildFile(it) }
    .forEach { includeModule(it) }

private fun isNotRootModule(it: File, directory: File) = it != directory
private fun existBuildFile(it: File) = File(it, "build.gradle.kts").exists()

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
