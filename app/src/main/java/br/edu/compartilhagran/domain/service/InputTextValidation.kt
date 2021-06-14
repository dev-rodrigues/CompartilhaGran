package br.edu.compartilhagran.domain.service

import br.edu.compartilhagran.domain.objectvalue.InputText
import br.edu.compartilhagran.domain.objectvalue.UiMessageError
import java.util.*
import kotlin.collections.ArrayList

interface InputTextValidation {
    fun validate(inputs: List<InputText>): ArrayList<UiMessageError>?
}