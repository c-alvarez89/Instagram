package com.meazza.instagram.ui.add_post.from

import java.io.File
import java.util.*

object FileSearch {

    fun getDirectoryPaths(directory: String): ArrayList<String> {
        val pathArray = ArrayList<String>()
        val file = File(directory)
        val listFiles = file.listFiles()
        for (i in listFiles.indices) {
            if (listFiles[i].isDirectory) {
                pathArray.add(listFiles[i].absolutePath)
            }
        }
        return pathArray
    }

    fun getFilePaths(directory: String): ArrayList<String> {
        val pathArray = ArrayList<String>()
        val file = File(directory)
        val listFiles = file.listFiles()
        for (i in listFiles.indices) {
            if (listFiles[i].isFile) {
                pathArray.add(listFiles[i].absolutePath)
            }
        }
        return pathArray
    }
}