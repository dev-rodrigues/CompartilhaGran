package br.edu.compartilhagran.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.compartilhagran.domain.entity.Annotation
import br.edu.compartilhagran.infrastructure.service.AnnotationService
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService

class HomeViewModel(
    private val firebaseAuthService: FirebaseAuthService,
    private val annotationService: AnnotationService
) : ViewModel() {

    fun findAnnotationsToUser() {
    }


    private val _annotations = MutableLiveData<List<Annotation>>();
    val annotations: LiveData<List<Annotation>>
        get() = _annotations
}