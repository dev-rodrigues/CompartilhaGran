package br.edu.compartilhagran.infrastructure.dao

import br.edu.compartilhagran.domain.entity.UserDetail
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

interface UserDetailDAO:DAO<UserDetail, String> {
    fun monitorInBackground(key: String): Query
    fun findBy(key: String): Task<QuerySnapshot>
}