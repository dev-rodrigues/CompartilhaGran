package br.edu.compartilhagran.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService
import br.edu.compartilhagran.infrastructure.service.UserDetailService

class SignupViewModelFactory(
    private val firebaseAuthService: FirebaseAuthService,
    private val userDetailService: UserDetailService
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java))
            return SignupViewModel(firebaseAuthService, userDetailService) as T
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}