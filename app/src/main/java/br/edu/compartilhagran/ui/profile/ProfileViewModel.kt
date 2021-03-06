package br.edu.compartilhagran.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.compartilhagran.domain.entity.Annotation
import br.edu.compartilhagran.domain.entity.UserDetail
import br.edu.compartilhagran.infrastructure.service.AnnotationService
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService
import br.edu.compartilhagran.infrastructure.service.UserDetailService

class ProfileViewModel(
    private val userDetailService: UserDetailService,
    private val annotationService: AnnotationService,
    private val firebaseAuthService: FirebaseAuthService,
) : ViewModel() {

    private var userKey: String = firebaseAuthService.getUser().email!!

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _annotations = MutableLiveData<List<Annotation>>();
    val annotations: LiveData<List<Annotation>>
        get() = _annotations

    private val _userDetails = MutableLiveData<List<UserDetail>>();
    val userDetails: LiveData<List<UserDetail>>
        get() = _userDetails

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    init {
        monitorin()
    }

    fun findDetailUser() {
        val task = userDetailService.findUserDetailBy(this.userKey)

        task
            .addOnSuccessListener {
                _userDetails.value = it.toObjects(UserDetail::class.java)
        }
            .addOnFailureListener {
                Log.e("AUTENTICACAO", it.message)
                _msg.value = "${it.message}"
        }

        annotationService.findBy(this.userKey).addOnSuccessListener {
            _annotations.value = it.toObjects(Annotation::class.java)
        }

    }

    fun editProfile(userName: String, nickName: String) {
        val task = userDetailService.findUserDetailBy(this.userKey)

        task
            .addOnSuccessListener {
                val list = it.toObjects(UserDetail::class.java)

                if (list.isNotEmpty()) {
                    val selected = list[0]

                    selected.fullName = userName
                    selected.nickName = nickName

                    val taskUpdate = userDetailService.updateUserDetails(selected.id!!, selected)

                    taskUpdate
                        .addOnSuccessListener {
                            _status.value = true
                        }
                        .addOnFailureListener {
                            _status.value = false
                        }
                } else {
                    val email = firebaseAuthService.getUser().email
                    val newUserDetail:UserDetail = UserDetail(null, email, userName, nickName)
                    val taskNewUser = userDetailService.register(newUserDetail)
                    taskNewUser
                        .addOnSuccessListener {
                            _status.value = true
                        }
                        .addOnFailureListener {
                            _status.value = false
                        }
                }
            }
            .addOnFailureListener {
                _msg.value = "${it.message}"
            }
    }

    fun monitorin() {
        userDetailService.monitorInBackground(this.userKey).addSnapshotListener {
                snapshot, error ->
                if (snapshot != null)
                    _userDetails.value = snapshot.toObjects(UserDetail::class.java)
        }
    }

}