package br.edu.compartilhagran.infrastructure.gateway.data

class Request(
    var type: String,
    var query: String,
    var language: String,
    var unit: String
)