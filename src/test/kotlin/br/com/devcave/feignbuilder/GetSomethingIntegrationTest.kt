package br.com.devcave.feignbuilder

import br.com.devcave.feignbuilder.decorator.MockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import com.github.tomakehurst.wiremock.common.Json
import io.restassured.RestAssured
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetSomethingIntegrationTest {

    private val mockServer: MockServer = MockServer()

    @BeforeEach
    internal fun setUp() {
        mockServer.start()
    }

    @AfterEach
    internal fun tearDown() {
        mockServer.stop()
    }

    @Test
    fun testGet() {
        mockServer.stubFor(
            get(urlMatching("/my-webhook(.*)"))
                .withQueryParam("id", equalTo("99"))
                .withQueryParam("name", equalTo("iundarigun"))
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(""" [ { "name": "iundarigun", "value": 99 } ] """)
                )
        )

        RestAssured
            .given()
                .log().all()
                .param("id", 99)
                .param("name", "iundarigun")
                .param("url", "http://localhost:8888/my-webhook")
            .`when`()
                .get("/feign")
            .then()
                .log().all()
                .statusCode(200)

        WireMock.verify(
            1,
            getRequestedFor(urlMatching("/my-webhook(.*)"))
                .withQueryParam("id", equalTo("99"))
                .withQueryParam("name", equalTo("iundarigun"))
        )
    }

}
