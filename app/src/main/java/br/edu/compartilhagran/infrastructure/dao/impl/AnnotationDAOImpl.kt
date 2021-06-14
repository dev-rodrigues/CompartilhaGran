package br.edu.compartilhagran.infrastructure.dao.impl

import br.edu.compartilhagran.domain.entity.Annotation
import br.edu.compartilhagran.infrastructure.dao.AnnotationDAO
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore

class AnnotationDAOImpl: AnnotationDAO {

    var db = FirebaseFirestore.getInstance().collection("annotation")

    override fun store(entity: Annotation): Task<Void> {
        return db.document().set(entity)
    }

    override fun destroy(key: String): Task<Void> {
        throw NotImplementedError("função não implementada")
    }

    override fun update(entity: Annotation, key: String): Task<Void> {
        throw NotImplementedError("função não implementada")
    }
}