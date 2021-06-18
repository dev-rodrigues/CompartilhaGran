package br.edu.compartilhagran.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.compartilhagran.domain.entity.UserDetail
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService
import br.edu.compartilhagran.infrastructure.service.UserDetailService

class SignupViewModel(
    private val firebaseAuthService: FirebaseAuthService,
    private val userDetailService: UserDetailService
) : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg


    fun register(email: String, password: String, fullName: String) {
        val task = firebaseAuthService.createUserWithEmailAndPassword(email, password);

        task
            .addOnSuccessListener {
                _msg.value = "Cadastro no FirebaseAuth realizado com sucesso."

                var userDetail = UserDetail(null, email, fullName)
                val taskDetail = userDetailService.register(userDetail)

                taskDetail
                    .addOnSuccessListener {
                        _status.value = true
                        _msg.value = "User detail sucessfully registered"
                    }
                    .addOnFailureListener {
                        _msg.value = it.message
                    }

            }
            .addOnFailureListener {
                _msg.value = "${it.message}"
            }
    }

}