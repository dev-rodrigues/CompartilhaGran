package br.edu.compartilhagran.infrastructure.service

import br.edu.compartilhagran.domain.entity.UserDetail
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

interface UserDetailService {
    fun register(userDetail: UserDetail): Task<Void>
    fun findUserDetailBy(key: String): Task<QuerySnapshot>
    fun monitorInBackground(key: String): Query
}