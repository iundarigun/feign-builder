package br.com.devcave.feignbuilder.decorator

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer
import com.github.tomakehurst.wiremock.stubbing.StubMapping

class MockServer(
    private val wireMockServer: WireMockServer = WireMockServer(
        WireMockConfiguration()
            .extensions(ResponseTemplateTransformer(false))
            .port(8888)
    )
) {

    fun start() {
        wireMockServer.start()
        WireMock.configureFor(wireMockServer.port())
    }

    fun stop() {
        wireMockServer.stop()
        wireMockServer.shutdownServer()
    }

    fun stubFor(mappingBuilder: MappingBuilder): StubMapping {
        return wireMockServer.stubFor(mappingBuilder)
    }

}
