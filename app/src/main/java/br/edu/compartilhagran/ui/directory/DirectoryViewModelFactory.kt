package br.edu.compartilhagran.ui.directory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.edu.compartilhagran.infrastructure.service.DeleteFileService
import br.edu.compartilhagran.infrastructure.service.FilesOnDirectory

class DirectoryViewModelFactory(
    var filesOnDirectory: FilesOnDirectory,
    var deleteFileService: DeleteFileService
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DirectoryViewModel::class.java))
            return DirectoryViewModel(filesOnDirectory, deleteFileService) as T
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}