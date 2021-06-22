package br.edu.compartilhagran.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.compartilhagran.domain.entity.Annotation
import br.edu.compartilhagran.domain.entity.UserDetail
import br.edu.compartilhagran.infrastructure.gateway.ViaCepGateway
import br.edu.compartilhagran.infrastructure.gateway.WeatherstackGateway
import br.edu.compartilhagran.infrastructure.gateway.data.EnderecoDTO
import br.edu.compartilhagran.infrastructure.gateway.data.WeatherResponse
import br.edu.compartilhagran.infrastructure.gateway.impl.ViaCepGatewayImpl
import br.edu.compartilhagran.infrastructure.gateway.impl.WeatherstackGatewayImpl
import br.edu.compartilhagran.infrastructure.service.AnnotationService
import br.edu.compartilhagran.infrastructure.service.FindWeatherService
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService
import br.edu.compartilhagran.infrastructure.service.UserDetailService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class DashboardViewModel(
    private val firebaseAuthService: FirebaseAuthService,
    private val annotationService: AnnotationService,
    private val userDetailService: UserDetailService
) : ViewModel() {

    var weatherstackGateway: WeatherstackGateway = WeatherstackGatewayImpl()

    private val _userDetails = MutableLiveData<List<UserDetail>>();
    val userDetails: LiveData<List<UserDetail>>
        get() = _userDetails

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    private var response: WeatherResponse ?=null


    suspend fun saveAnnotation(
        title: String,
        description: String,
        picture: String,
        LATITUDE: String?,
        LONGITUDE: String?
    ) {
        val emailKey = firebaseAuthService.getUser().email!!
        findUserDetail(emailKey)

        var newAnnotation: Annotation ?= null

        val coroutine = CoroutineScope(Dispatchers.IO).async {
            val query = "$LATITUDE,$LONGITUDE"
            response = weatherstackGateway.getWeather(query)!!
        }

        coroutine.await()

        val userDetail = _userDetails.value?.get(0)

        newAnnotation = Annotation(
            null,
            emailKey,
            Calendar.getInstance().time,
            title,
            description,
            picture,
            response?.location?.country,
            response?.location?.region,
            response?.current?.temperature,
            response?.current?.weather_icons?.get(0),
            userDetail?.picture,
            userDetail?.fullName,
            userDetail?.nickName
        )
        save(newAnnotation)
    }

    private fun findUserDetail(email: String) {
        userDetailService.findUserDetailBy(email).addOnSuccessListener {
            _userDetails.value = it.toObjects(UserDetail::class.java)
        }
    }

    private fun save(newAnnotation: Annotation?) {
        val task = annotationService.storage(newAnnotation!!)

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