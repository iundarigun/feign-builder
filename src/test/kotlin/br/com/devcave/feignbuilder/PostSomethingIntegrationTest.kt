package br.com.devcave.feignbuilder

import br.com.devcave.feignbuilder.decorator.MockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.equalToJson
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.common.Json
import io.restassured.RestAssured
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostSomethingIntegrationTest {

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
    fun testPost() {
        mockServer.stubFor(
            post(urlEqualTo("/my-webhook"))
                .withRequestBody(equalToJson(""" { "id": 99, "name": "iundarigun" } """))
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(""" [ { "value": 99, "name": "iundarigun" } ] """)
                )
        )

        RestAssured
            .given()
                .log().all()
                .param("name", "iundarigun")
                .param("id", 99)
                .param("url", "http://localhost:8888/my-webhook")
            .`when`()
                .post("/feign")
            .then()
                .log().all()
                .statusCode(200)
    }

}
