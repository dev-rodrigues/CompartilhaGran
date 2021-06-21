package br.edu.compartilhagran.infrastructure.gateway.data

class WeatherResponse (
    var request: Request,
    var location: Location,
    var current: Current
)