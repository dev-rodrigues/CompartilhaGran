package br.edu.compartilhagran.ui.home

import android.widget.Toast
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

    private val _annotations = MutableLiveData<List<Annotation>>();
    val annotations: LiveData<List<Annotation>>
        get() = _annotations

    init {
        teste()
    }

    fun findAnnotationsToUser() {
        var emailKey = firebaseAuthService.getUser().email!!
        annotationService.findBy(emailKey).addOnSuccessListener {
            _annotations.value = it.toObjects(Annotation::class.java)
        }
    }

    fun teste() {
        annotationService.monitorInBackground("teste@teste.com.br").addSnapshotListener {
            snapshot, error ->
            if (snapshot != null)
                _annotations.value = snapshot.toObjects(Annotation::class.java)

//            snapshot?.toObjects(Annotation::class.java)
            println("CUCO!")
        }
    }
}