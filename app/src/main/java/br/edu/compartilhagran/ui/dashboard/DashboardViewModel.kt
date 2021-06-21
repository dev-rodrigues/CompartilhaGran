package br.edu.compartilhagran.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.compartilhagran.domain.entity.Annotation
import br.edu.compartilhagran.infrastructure.gateway.ViaCepGateway
import br.edu.compartilhagran.infrastructure.gateway.WeatherstackGateway
import br.edu.compartilhagran.infrastructure.gateway.data.EnderecoDTO
import br.edu.compartilhagran.infrastructure.gateway.data.WeatherResponse
import br.edu.compartilhagran.infrastructure.gateway.impl.ViaCepGatewayImpl
import br.edu.compartilhagran.infrastructure.gateway.impl.WeatherstackGatewayImpl
import br.edu.compartilhagran.infrastructure.service.AnnotationService
import br.edu.compartilhagran.infrastructure.service.FindWeatherService
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DashboardViewModel(
    private val firebaseAuthService: FirebaseAuthService,
    private val annotationService: AnnotationService,
    private val findWeatherService: FindWeatherService
) : ViewModel() {

    var weatherstackGateway: WeatherstackGateway = WeatherstackGatewayImpl()

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    private lateinit var response: WeatherResponse
    private lateinit var enderecoLocalizado: EnderecoDTO

    fun Double.format(digits: Int) = "%.${digits}f".format(this)

    fun saveAnnotation(
        title: String,
        description: String,
        picture: String,
        LATITUDE: Double?,
        LONGITUDE: Double?
    ) {

        // TODO: ESTÁ TRAVANDO AO REALIZAR REQUISICAO
//        if (LATITUDE != null && LONGITUDE != null) {
//            val parameter = "$LATITUDE,$LONGITUDE"
//            CoroutineScope(Dispatchers.IO).launch {
//                val LatitudeToString = -22.928 //LATITUDE?.format(3)
//                val LongitudeToString = -43.363//LONGITUDE?.format(3)
//
//                response = weatherstackGateway.getWeather(LatitudeToString, LongitudeToString)
//                println(response?.current?.temperature)
//            }
//        }

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