package br.edu.compartilhagran.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService

class LoginViewModelFactory(
    private val firebaseAuthService: FirebaseAuthService
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java))
            return LoginViewModel(firebaseAuthService) as T
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}