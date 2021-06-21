package br.edu.compartilhagran.infrastructure.service

import android.content.Context
import java.io.File

interface FilesDirTxtService {
    fun getFiles(context: Context): List<File>
}