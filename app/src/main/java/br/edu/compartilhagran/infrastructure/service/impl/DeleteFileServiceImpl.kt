package br.edu.compartilhagran.infrastructure.service.impl

import android.content.Context
import br.edu.compartilhagran.infrastructure.service.DeleteFileService
import br.edu.compartilhagran.infrastructure.service.FilesDirTxtService

class DeleteFileServiceImpl: DeleteFileService {

    var filesDir: FilesDirTxtService = FilesDirTxtServiceImpl()

    override fun execute(fileName: String, context: Context) {
        val files = filesDir.getFiles(context)

        val selectedFile = files.filter { it.name == fileName }
        selectedFile[0].delete()
    }
}