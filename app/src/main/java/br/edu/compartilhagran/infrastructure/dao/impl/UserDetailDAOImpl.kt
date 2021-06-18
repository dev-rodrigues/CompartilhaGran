package br.edu.compartilhagran.infrastructure.dao.impl

import br.edu.compartilhagran.domain.entity.UserDetail
import br.edu.compartilhagran.infrastructure.dao.UserDetailDAO
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class UserDetailDAOImpl: UserDetailDAO {

    var db = FirebaseFirestore.getInstance().collection("userdetail")

    override fun monitorInBackground(key: String): Query {
        return db.whereEqualTo("foreignKey", key)
    }

    override fun findBy(key: String): Task<QuerySnapshot> {
        return db.whereEqualTo("foreignKey", key).get()
    }

    override fun store(entity: UserDetail): Task<Void> {
        return db.document().set(entity)
    }

    override fun destroy(key: String): Task<Void> {
        throw NotImplementedError("função não implementada")
    }

    override fun update(entity: UserDetail, key: String): Task<Void> {
        return db.document(key).set(entity)
    }
}