package br.edu.compartilhagran.infrastructure.gateway.configuration

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitConfiguration {

    private val URL_BASE = "http://api.weatherstack.com/current?access_key=b71f8c9068c0ad385666450832243d4b&query=" //-22.928,-43.363

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

//    fun getInstanceViaCep(): ViaCepGateway = getInstance().create(ViaCepGateway::class.java);
}