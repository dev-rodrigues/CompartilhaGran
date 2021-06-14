package br.edu.compartilhagran.infrastructure.service.impl

import br.edu.compartilhagran.domain.entity.UserDetail
import br.edu.compartilhagran.infrastructure.dao.impl.UserDetailDAOImpl
import br.edu.compartilhagran.infrastructure.service.UserDetailService
import com.google.android.gms.tasks.Task

class UserDetailServiceImpl: UserDetailService {

    private var userDetailDAO: UserDetailDAOImpl = UserDetailDAOImpl()

    override fun register(userDetail: UserDetail): Task<Void> {
        return userDetailDAO.store(userDetail);
    }
}