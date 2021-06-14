package br.edu.compartilhagran.infrastructure.service.impl

import br.edu.compartilhagran.domain.entity.Annotation
import br.edu.compartilhagran.infrastructure.dao.impl.AnnotationDAOImpl
import br.edu.compartilhagran.infrastructure.service.AnnotationService
import com.google.android.gms.tasks.Task

class AnnotationServiceImpl: AnnotationService {

    private var dao: AnnotationDAOImpl = AnnotationDAOImpl()

    override fun storage(annotation: Annotation): Task<Void> {
        return dao.store(annotation)
    }
}