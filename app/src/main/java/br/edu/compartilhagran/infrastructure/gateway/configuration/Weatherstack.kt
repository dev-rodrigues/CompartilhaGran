package br.edu.compartilhagran.infrastructure.gateway.configuration

import retrofit2.http.GET
import retrofit2.http.Path

interface Weatherstack {

    @GET("{location}")
    suspend fun getWeather(@Path("location") location: String)
}