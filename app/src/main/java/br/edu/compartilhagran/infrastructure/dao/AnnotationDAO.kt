package br.edu.compartilhagran.infrastructure.dao

import br.edu.compartilhagran.domain.entity.Annotation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

interface AnnotationDAO:DAO<Annotation, String>{
    fun findBy(key: String): Task<QuerySnapshot>
    fun find(): Task<QuerySnapshot>
    fun monitorInBackground(key: String): Query
}