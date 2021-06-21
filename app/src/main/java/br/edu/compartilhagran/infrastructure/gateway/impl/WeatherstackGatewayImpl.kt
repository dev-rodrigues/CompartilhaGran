package br.edu.compartilhagran.infrastructure.gateway.impl

import br.edu.compartilhagran.infrastructure.gateway.WeatherstackGateway
import br.edu.compartilhagran.infrastructure.gateway.configuration.RetrofitConfiguration
import br.edu.compartilhagran.infrastructure.gateway.data.WeatherResponse

class WeatherstackGatewayImpl: WeatherstackGateway {
    override suspend fun getWeather(query:String): WeatherResponse? {
        return RetrofitConfiguration().getInstanceWeather().getWeather(query)
    }
}