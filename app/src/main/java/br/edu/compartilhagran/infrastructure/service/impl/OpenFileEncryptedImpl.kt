package br.edu.compartilhagran.infrastructure.service.impl

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import br.edu.compartilhagran.infrastructure.service.OpenFileEncrypted
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class OpenFileEncryptedImpl: OpenFileEncrypted {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun execute(fileName: String, context:Context): ArrayList<String> {

        val values: ArrayList<String> = ArrayList()
        val encryptedFile = getEncFile(fileName, context).openFileInput()
        val br = BufferedReader(InputStreamReader(encryptedFile))

        br.lines().forEach {
            values.add(it)
        }

        encryptedFile.close()
        return values
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getEncFile(fileName: String, context:Context): EncryptedFile {
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val file = File(context?.filesDir, fileName)

        return EncryptedFile.Builder(
            file,
            context,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB)
            .build()
    }
}