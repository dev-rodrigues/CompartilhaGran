package br.edu.compartilhagran.infrastructure.dao.impl

import br.edu.compartilhagran.domain.entity.UserDetail
import br.edu.compartilhagran.infrastructure.dao.UserDetailDAO
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore

class UserDetailDAOImpl: UserDetailDAO {

    var db = FirebaseFirestore.getInstance().collection("userdetail")

    override fun store(entity: UserDetail): Task<Void> {
        return db.document().set(entity)
    }

    override fun destroy(key: String): Task<Void> {
        throw NotImplementedError("função não implementada")
    }

    override fun update(entity: UserDetail, key: String): Task<Void> {
        throw NotImplementedError("função não implementada")
    }
}