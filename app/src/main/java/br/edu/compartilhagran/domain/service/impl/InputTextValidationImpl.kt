package br.edu.compartilhagran.domain.service.impl

import android.os.Build
import androidx.annotation.RequiresApi
import br.edu.compartilhagran.domain.objectvalue.InputText
import br.edu.compartilhagran.domain.objectvalue.TypeError
import br.edu.compartilhagran.domain.objectvalue.UiMessageError
import br.edu.compartilhagran.domain.service.InputTextValidation
import java.util.*
import kotlin.collections.ArrayList


class InputTextValidationImpl: InputTextValidation {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun validate(inputs: List<InputText>): ArrayList<UiMessageError>? {
        var messages = ArrayList<UiMessageError>()

        inputs.forEach {
            if (it.message.isNullOrBlank()) {
                messages.add(UiMessageError(it.componentName, "O campo ${it.componentName} é obrigatório", TypeError.VALIDATION))
            }
        }
        return Optional.of(messages).orElse(null);
    }
}