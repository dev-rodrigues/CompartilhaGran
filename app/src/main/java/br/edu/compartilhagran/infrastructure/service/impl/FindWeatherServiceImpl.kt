package br.edu.compartilhagran.infrastructure.service.impl

import br.edu.compartilhagran.infrastructure.gateway.WeatherstackGateway
import br.edu.compartilhagran.infrastructure.gateway.data.WeatherResponse
import br.edu.compartilhagran.infrastructure.gateway.impl.WeatherstackGatewayImpl
import br.edu.compartilhagran.infrastructure.service.FindWeatherService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FindWeatherServiceImpl: FindWeatherService {

    var weatherstackGateway: WeatherstackGateway = WeatherstackGatewayImpl()

    override fun execute(localization: String):WeatherResponse? {
        var response: WeatherResponse ?= null
        CoroutineScope(Dispatchers.IO).launch {
            response = weatherstackGateway.getWeather(localization)
        }
        return response
    }
}