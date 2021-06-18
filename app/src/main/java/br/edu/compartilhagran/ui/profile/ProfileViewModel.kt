package br.edu.compartilhagran.ui.profile

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
        var task = userDetailService.findUserDetailBy(this.userKey)

        task
            .addOnSuccessListener {
                _userDetails.value = it.toObjects(UserDetail::class.java)
        }
            .addOnFailureListener {
                _msg.value = "${it.message}"
        }

        annotationService.findBy(this.userKey).addOnSuccessListener {
            _annotations.value = it.toObjects(Annotation::class.java)
        }

    }

    fun editProfile(userName: String, nickName: String) {

    }

    fun monitorin() {
        userDetailService.monitorInBackground(this.userKey).addSnapshotListener {
                snapshot, error ->
                if (snapshot != null)
                    _userDetails.value = snapshot.toObjects(UserDetail::class.java)
        }
    }

}