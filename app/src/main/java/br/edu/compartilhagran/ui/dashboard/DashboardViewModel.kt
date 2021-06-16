package br.edu.compartilhagran.ui.dashboard

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.compartilhagran.domain.entity.Annotation
import br.edu.compartilhagran.infrastructure.service.AnnotationService
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService
import java.io.ByteArrayOutputStream
import java.util.*

class DashboardViewModel(
    private val firebaseAuthService: FirebaseAuthService,
    private val annotationService: AnnotationService
) : ViewModel() {


    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun saveAnnotation(title: String, description: String, picture: String) {

        var emailKey = firebaseAuthService.getUser().email

        var newAnnotation = Annotation(
            null,
            emailKey,
            Calendar.getInstance().time,
            title,
            description,
            picture
        )

        var task = annotationService.storage(newAnnotation)

        task
            .addOnSuccessListener {
                _status.value = true
                _msg.value = "Image saved successfully"
            }
            .addOnFailureListener {
                Log.e(DashboardViewModel::class.java.name.toString(), "${it.message}")
                _msg.value = "${it.message}"
            }
    }
}