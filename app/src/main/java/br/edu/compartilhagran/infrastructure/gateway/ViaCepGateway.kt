package br.edu.compartilhagran.infrastructure.gateway

import br.edu.compartilhagran.infrastructure.gateway.data.EnderecoDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepGateway {

    @GET("{cep}/json/")
    suspend fun getEndereco(@Path("cep") cep: String): EnderecoDTO?
}