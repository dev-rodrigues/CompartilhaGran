package br.edu.compartilhagran.infrastructure.service.impl

import android.content.Context
import br.edu.compartilhagran.infrastructure.service.FilesDirTxtService
import br.edu.compartilhagran.infrastructure.service.FilesOnDirectory
import br.edu.compartilhagran.infrastructure.service.data.FileDTO

class FilesOnDirectoryImpl: FilesOnDirectory {

    var filesDir: FilesDirTxtService = FilesDirTxtServiceImpl()

    override fun execute(context: Context): List<FileDTO> {
        var response = ArrayList<FileDTO>()

        filesDir.getFiles(context).forEach {
            response.add(FileDTO(it.name, it.length(), it.path))
        }

        return response
    }
}