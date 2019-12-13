package br.com.devcave.feignbuilder

import br.com.devcave.feignbuilder.decorator.MockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
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
            get(urlEqualTo("/my-webhook?name=iundarigun&id=1"))
                .withQueryParam("name", equalTo("iundarigun"))
                .withQueryParam("id", equalTo("1"))
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(""" [ { "name": "iundarigun", "value": 99 } ] """)
                )
        )

        RestAssured
            .given()
                .log().all()
                .param("name", "iundarigun")
                .param("id", 1)
                .param("url", "http://localhost:8888/my-webhook")
            .`when`()
                .get("/feign")
            .then()
                .log().all()
                .statusCode(200)
    }

}
