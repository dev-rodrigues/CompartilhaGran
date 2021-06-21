package br.edu.compartilhagran.infrastructure.service.impl

import android.content.Context
import br.edu.compartilhagran.infrastructure.service.FilesDirTxtService
import java.io.File

class FilesDirTxtServiceImpl: FilesDirTxtService {
    override fun getFiles(context: Context): List<File> {
        val files = context.filesDir.listFiles()
        return files.filter { it.extension == "txt" }
    }
}