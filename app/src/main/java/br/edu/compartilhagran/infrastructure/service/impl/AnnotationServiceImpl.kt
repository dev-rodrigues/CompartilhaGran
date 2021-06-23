package br.edu.compartilhagran.infrastructure.service.impl

import br.edu.compartilhagran.domain.entity.Annotation
import br.edu.compartilhagran.infrastructure.dao.impl.AnnotationDAOImpl
import br.edu.compartilhagran.infrastructure.service.AnnotationService
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class AnnotationServiceImpl: AnnotationService {

    private var dao: AnnotationDAOImpl = AnnotationDAOImpl()

    override fun storage(annotation: Annotation): Task<Void> {
        return dao.store(annotation)
    }

    override fun findBy(email: String): Task<QuerySnapshot> {
        return dao.findBy(email)
    }

    override fun list(): Task<QuerySnapshot> {
        return dao.find()
    }

    override fun monitorInBackground(key: String): Query {
        return dao.monitorInBackground(key)
    }

    override fun editAnnotation(annotation: Annotation) {
        dao.update(annotation, annotation.id.toString())
    }

    override fun deleteAnnotation(annotation: Annotation) {
        dao.destroy(annotation.id.toString())
    }
}