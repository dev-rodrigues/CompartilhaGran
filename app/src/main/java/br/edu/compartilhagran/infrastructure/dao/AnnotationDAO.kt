package br.edu.compartilhagran.infrastructure.dao

import br.edu.compartilhagran.domain.entity.Annotation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface AnnotationDAO:DAO<Annotation, String>{
    fun findBy(key: String): Task<QuerySnapshot>;
}