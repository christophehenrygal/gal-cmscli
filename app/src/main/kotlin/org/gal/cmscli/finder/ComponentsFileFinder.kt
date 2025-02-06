package org.gal.cmscli.finder

import java.io.File

const val DEFAULT_COMPONENT_FILE_NAME = "galsmarteditmodule-items.xml"
const val DEFAULT_BEAN_FILE_NAME= "galsmarteditmodule-spring.xml"
const val HOME_FOLDER_PROPERTY = "user.home"
const val GAL_HYBRIS_PATH_PART = "/gal-hybris/"
const val GAL_HYBRIS_RESOURCES_PART = "/resources/"
const val CACHE_FILE_PATH = "./componentFileCache.txt"

fun findSmartEditComponentsFile() = findFile(DEFAULT_COMPONENT_FILE_NAME)
fun findSmartEditBeansFile() = findFile(DEFAULT_BEAN_FILE_NAME)

fun findFile(fileName: String): File? {
    val cachedFile = mayFindCachedFile(fileName)
    if (cachedFile != null) return cachedFile
    val rootDirectory = File(System.getProperty(HOME_FOLDER_PROPERTY))
    return rootDirectory.walk().find {
        it.absolutePath.contains(GAL_HYBRIS_PATH_PART)
                && it.absolutePath.contains(fileName)
                && it.absolutePath.contains(GAL_HYBRIS_RESOURCES_PART)
    }?.also { cacheFile(it, fileName) }
}

private fun mayFindCachedFile(fileName: String): File? {
    val cacheFile = File(fileName.toTxt())
    return if (cacheFile.exists()) File(cacheFile.readText())
    else null
}

private fun cacheFile(file: File, fileName: String) {
    val cacheFile = File(fileName.toTxt())
    cacheFile.writeText(file.absolutePath)
}

private fun String.toTxt() = this.replace(".xml", "Cache.txt")