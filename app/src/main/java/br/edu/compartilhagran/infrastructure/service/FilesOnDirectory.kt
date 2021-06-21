package br.edu.compartilhagran.infrastructure.service

import android.content.Context
import br.edu.compartilhagran.infrastructure.service.data.FileDTO

interface FilesOnDirectory {
    fun execute(context: Context):List<FileDTO>
}