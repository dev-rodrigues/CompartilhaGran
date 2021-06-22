package br.edu.compartilhagran.infrastructure.service.impl

import android.annotation.SuppressLint
import android.content.Context
import br.edu.compartilhagran.domain.objectvalue.InputText
import br.edu.compartilhagran.infrastructure.service.EncryptedService
import br.edu.compartilhagran.infrastructure.service.StoreFile
import java.io.PrintWriter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StoreFileImpl: StoreFile {

    private var encryptedService: EncryptedService = EncryptedServiceImpl()

    @SuppressLint("SimpleDateFormat")
    override fun execute(inputs: ArrayList<InputText>, context: Context) {
        val now = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd.MM.yyy hh:mm:mm")
        val encryptedOut = encryptedService.encrypt(inputs[0].message + dateFormat.format(now), context)!!.openFileOutput()
        val pw = PrintWriter(encryptedOut)

        inputs.forEach{
            pw.println(it.message)
        }

        pw.flush()
        encryptedOut.close()
    }
}