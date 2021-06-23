package br.edu.compartilhagran.ui.showAnnotation

import androidx.lifecycle.ViewModel
import br.edu.compartilhagran.infrastructure.service.impl.AnnotationServiceImpl
import br.edu.compartilhagran.domain.entity.Annotation

class ShowAnnotationViewModel : ViewModel() {

    val service = AnnotationServiceImpl()

    fun deleteAnnotation(annotation: Annotation) {
        service.deleteAnnotation(annotation)
    }

    fun editAnnotation(annotation: Annotation) {
        service.editAnnotation(annotation)
    }
}