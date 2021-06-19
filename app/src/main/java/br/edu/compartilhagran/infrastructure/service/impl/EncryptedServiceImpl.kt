package br.edu.compartilhagran.infrastructure.service.impl

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import br.edu.compartilhagran.infrastructure.service.EncryptedService
import java.io.File

class EncryptedServiceImpl: EncryptedService {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun encrypt(name: String, requireContext: Context): EncryptedFile {
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val fileName = "$name.txt"
        val file = File(requireContext.filesDir, fileName)

        return EncryptedFile.Builder(
            file,
            requireContext,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB).build()
    }
}