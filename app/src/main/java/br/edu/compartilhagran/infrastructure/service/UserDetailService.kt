package br.edu.compartilhagran.infrastructure.service

import br.edu.compartilhagran.domain.entity.UserDetail
import com.google.android.gms.tasks.Task

interface UserDetailService {
    fun register(userDetail: UserDetail): Task<Void>;
}