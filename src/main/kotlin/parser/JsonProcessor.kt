package parser

import java.nio.file.Files.*
import java.nio.file.Paths.*
import java.util.stream.Collectors

class JsonProcessor {

    companion object {
        fun getFilesNamesAtResources(path: String): List<String> {
            val projectDirAbsolutePath = get("").toAbsolutePath().toString()
            val resourcesPath = get(projectDirAbsolutePath, path)
            return walk(resourcesPath)
                .filter { item -> isRegularFile(item) }
                .map { it.toString() }.collect(Collectors.toList())
        }
    }
}
