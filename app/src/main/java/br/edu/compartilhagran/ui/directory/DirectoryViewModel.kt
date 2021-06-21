package br.edu.compartilhagran.ui.directory

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.compartilhagran.infrastructure.service.DeleteFileService
import br.edu.compartilhagran.infrastructure.service.FilesOnDirectory
import br.edu.compartilhagran.infrastructure.service.data.FileDTO

class DirectoryViewModel(
    var filesOnDirectory: FilesOnDirectory,
    var deleteFileService: DeleteFileService
) : ViewModel() {

    private val _files = MutableLiveData<List<FileDTO>>();
    val files: LiveData<List<FileDTO>>
        get() = _files

    fun findFilesToDirectory(context: Context) {
        _files.value = arrayListOf()
        _files.value = filesOnDirectory.execute(context);
    }

    fun deleteFile(fileName: String, context: Context) {
        deleteFileService.execute(fileName, context)
    }
}