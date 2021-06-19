package br.edu.compartilhagran.infrastructure.service

import android.content.Context
import br.edu.compartilhagran.domain.objectvalue.InputText

interface StoreFile {
    fun execute(inputs: ArrayList<InputText>, context: Context)
}