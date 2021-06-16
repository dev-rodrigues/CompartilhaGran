package br.edu.compartilhagran.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService

class LoginViewModel(
    private val firebaseAuthService: FirebaseAuthService
) : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    private val _ok = MutableLiveData<Boolean>()
    val ok: LiveData<Boolean> = _ok

    fun authenticate(email: String, password: String) {
        val task = firebaseAuthService.signIn(email, password);

        task
            .addOnSuccessListener {
                _status.value = true
                _msg.value = "Autenticado com sucesso"
                _ok.value = true
            }
            .addOnFailureListener {
                Log.e("LoginViewModel", "${it.message}")
                _msg.value = "${it.message}"
            }
    }

    fun logout() {
        firebaseAuthService.logout()
    }

}