package br.com.devcave.feignbuilder.testexecutionlistener

import io.restassured.RestAssured
import org.springframework.stereotype.Component
import org.springframework.test.context.event.BeforeTestMethodEvent
import org.springframework.test.context.event.annotation.BeforeTestMethod

@Component
class RestAssuredTestExecutionListener {

    @BeforeTestMethod
    fun portSetup(beforeTestMethodEvent: BeforeTestMethodEvent) {
        RestAssured.port = beforeTestMethodEvent.testContext
            .applicationContext
            .environment
            .getProperty("local.server.port", Int::class.java, RestAssured.port)
    }

}
