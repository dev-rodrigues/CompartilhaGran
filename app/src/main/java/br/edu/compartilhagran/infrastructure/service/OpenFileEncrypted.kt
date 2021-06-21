package br.edu.compartilhagran.infrastructure.service

import android.content.Context

interface OpenFileEncrypted {
    fun execute(fileName: String, context: Context): ArrayList<String>
}