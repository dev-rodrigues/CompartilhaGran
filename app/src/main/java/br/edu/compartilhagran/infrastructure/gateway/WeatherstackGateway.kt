package br.edu.compartilhagran.infrastructure.gateway

import br.edu.compartilhagran.infrastructure.gateway.data.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherstackGateway {

    @GET("current?access_key=7e8c5c9454ed712da5b2ffe2486e9418&query={long},{lat}")
    suspend fun getWeather(@Path("long") long: Double, @Path("lat") lat: Double): WeatherResponse?
}