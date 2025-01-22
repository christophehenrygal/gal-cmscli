package org.gal.cmscli.finder

import java.io.File

const val DEFAULT_COMPONENT_FILE_NAME = "galsmarteditmodule-items.xml"
const val HOME_FOLDER_PROPERTY = "user.home"
const val GAL_HYBRIS_PATH_PART = "/gal-hybris/"
const val GAL_HYBRIS_RESOURCES_PART = "/resources/"
const val CACHE_FILE_PATH = "./componentFileCache.txt"

fun findSmartEditComponentsFile(fileName: String = DEFAULT_COMPONENT_FILE_NAME): File? {
    val cachedFile = mayFindCachedFile()
    if (cachedFile != null) return cachedFile
    val rootDirectory = File(System.getProperty(HOME_FOLDER_PROPERTY))
    return rootDirectory.walk().find {
        it.absolutePath.contains(GAL_HYBRIS_PATH_PART)
                && it.absolutePath.contains(fileName)
                && it.absolutePath.contains(GAL_HYBRIS_RESOURCES_PART)
    }?.also { cacheFile(it) }
}

fun mayFindCachedFile(): File? {
    val cacheFile = File(CACHE_FILE_PATH)
    return if (cacheFile.exists()) File(cacheFile.readText())
    else null
}

fun cacheFile(componentFile: File) {
    val cacheFile = File(CACHE_FILE_PATH)
    cacheFile.writeText(componentFile.absolutePath)
}
