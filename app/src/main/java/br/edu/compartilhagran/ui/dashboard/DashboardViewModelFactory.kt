package br.edu.compartilhagran.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.edu.compartilhagran.infrastructure.service.AnnotationService
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService

class DashboardViewModelFactory(
    private val firebaseAuthService: FirebaseAuthService,
    private val annotationService: AnnotationService
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java))
            return DashboardViewModel(firebaseAuthService, annotationService) as T
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}