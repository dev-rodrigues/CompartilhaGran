package br.edu.compartilhagran.infrastructure.service.impl

import android.content.Context
import br.edu.compartilhagran.domain.objectvalue.InputText
import br.edu.compartilhagran.infrastructure.service.EncryptedService
import br.edu.compartilhagran.infrastructure.service.StoreFile
import java.io.PrintWriter

class StoreFileImpl: StoreFile {

    private var encryptedService: EncryptedService = EncryptedServiceImpl()

    override fun execute(inputs: ArrayList<InputText>, context: Context) {
        val encryptedOut = encryptedService.encrypt(inputs[0].message, context)!!.openFileOutput()
        val pw = PrintWriter(encryptedOut)

        inputs.forEach{
            pw.println(it.message)
        }

        pw.flush()
        encryptedOut.close()
    }
}