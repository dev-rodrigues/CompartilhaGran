package br.edu.compartilhagran.infrastructure.gateway.configuration

import br.edu.compartilhagran.infrastructure.gateway.ViaCepGateway
import br.edu.compartilhagran.infrastructure.gateway.WeatherstackGateway
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitConfiguration {

    //
    private val URL_BASE = "http://api.weatherstack.com/"


//    private val URL_BASE = "https://viacep.com.br/ws/"

    private var instance: Retrofit?= null

    private fun getInstance(): Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance as Retrofit
    }

    fun getInstanceWeather(): WeatherstackGateway = getInstance().create(WeatherstackGateway::class.java);
    fun getInstanceViaCep(): ViaCepGateway = getInstance().create(ViaCepGateway::class.java);
}