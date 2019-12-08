package br.com.devcave.feignbuilder.service

import br.com.devcave.feignbuilder.client.SomeHttpClient
import feign.Feign
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class ClientService(private val feignBuilder: Feign.Builder) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Cacheable("someHttpClient")
    fun getClient(url: String): SomeHttpClient {
        log.info("M=QueueBalancer, without cache")
        return feignBuilder.target(SomeHttpClient::class.java, url)
    }
}