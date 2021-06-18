package br.edu.compartilhagran.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.edu.compartilhagran.infrastructure.service.AnnotationService
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService
import br.edu.compartilhagran.infrastructure.service.UserDetailService

class ProfileViewModelFactory(
    private val userDetailService: UserDetailService,
    private val annotationService: AnnotationService,
    private val firebaseAuthService: FirebaseAuthService
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java))
            return ProfileViewModel(userDetailService, annotationService, firebaseAuthService) as T
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}