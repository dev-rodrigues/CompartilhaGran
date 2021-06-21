package br.edu.compartilhagran.infrastructure.service

import br.edu.compartilhagran.infrastructure.gateway.data.WeatherResponse

interface FindWeatherService {
    fun execute(localization:String): WeatherResponse?
}