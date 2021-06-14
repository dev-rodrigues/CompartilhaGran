package br.edu.compartilhagran.infrastructure.service

import br.edu.compartilhagran.domain.entity.Annotation
import com.google.android.gms.tasks.Task

interface AnnotationService {
    fun storage(annotation: Annotation): Task<Void>
}