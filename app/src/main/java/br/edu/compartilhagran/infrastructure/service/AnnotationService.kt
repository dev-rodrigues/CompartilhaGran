package br.edu.compartilhagran.infrastructure.service

import br.edu.compartilhagran.domain.entity.Annotation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

interface AnnotationService {
    fun storage(annotation: Annotation): Task<Void>
    fun findBy(email:String):Task<QuerySnapshot>
    fun list():Task<QuerySnapshot>
    fun monitorInBackground(key: String): Query
}