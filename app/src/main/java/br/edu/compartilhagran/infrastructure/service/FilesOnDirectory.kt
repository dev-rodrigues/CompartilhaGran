package br.edu.compartilhagran.infrastructure.service

import android.content.Context

interface FilesOnDirectory {
    fun execute(context: Context):List<String>
}