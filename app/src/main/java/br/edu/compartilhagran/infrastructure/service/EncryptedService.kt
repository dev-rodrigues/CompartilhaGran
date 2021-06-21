package br.edu.compartilhagran.infrastructure.service

import android.content.Context
import androidx.security.crypto.EncryptedFile

interface EncryptedService {

    fun encrypt(name: String, context: Context): EncryptedFile?
}