package br.edu.compartilhagran.infrastructure.gateway

import br.edu.compartilhagran.infrastructure.gateway.data.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherstackGateway {
    @GET("current?access_key=06679705370fe05c893cb7655f72d107")
    suspend fun getWeather(@Query("query")  query:String): WeatherResponse?
}