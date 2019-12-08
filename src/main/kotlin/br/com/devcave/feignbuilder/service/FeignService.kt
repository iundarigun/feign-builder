package br.com.devcave.feignbuilder.service

import br.com.devcave.feignbuilder.domain.SomeRequest
import br.com.devcave.feignbuilder.domain.SomeResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service


@Service
class FeignService(
    private val clientService: ClientService
) {

    fun getSomeResponseByGet(url: String, someRequest: SomeRequest): List<SomeResponse>? {

        return clientService
            .getClient(url)
            .getSomething(createHeader(), someRequest)
    }

    fun getSomeResponseByPost(url: String, someRequest: SomeRequest): List<SomeResponse>? {
        return clientService
            .getClient(url)
            .getSomething(createHeader(), someRequest)

    }

    private fun createHeader(): HashMap<String, String> {
        val map: HashMap<String, String> = HashMap()
        map["some-header"] = "some-value"
        return map
    }
}
