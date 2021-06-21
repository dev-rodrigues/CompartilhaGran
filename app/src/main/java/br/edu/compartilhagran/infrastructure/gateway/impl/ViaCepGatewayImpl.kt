package br.edu.compartilhagran.infrastructure.gateway.impl

import br.edu.compartilhagran.infrastructure.gateway.ViaCepGateway
import br.edu.compartilhagran.infrastructure.gateway.configuration.RetrofitConfiguration
import br.edu.compartilhagran.infrastructure.gateway.data.EnderecoDTO


class ViaCepGatewayImpl: ViaCepGateway {

    override suspend fun getEndereco(cep: String): EnderecoDTO? {
        return RetrofitConfiguration().getInstanceViaCep().getEndereco(cep)
    }


}