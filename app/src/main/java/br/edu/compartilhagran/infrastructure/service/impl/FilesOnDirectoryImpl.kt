package br.edu.compartilhagran.infrastructure.service.impl

import android.content.Context
import br.edu.compartilhagran.infrastructure.service.FilesOnDirectory

class FilesOnDirectoryImpl: FilesOnDirectory {
    override fun execute(context: Context): List<String> {

        var files = context.filesDir

        files.name
        files.path
        files.totalSpace

        return listOf()
    }
}