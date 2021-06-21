package br.edu.compartilhagran.infrastructure.service

import android.content.Context

interface DeleteFileService {
    fun execute(fileName: String, context: Context)
}