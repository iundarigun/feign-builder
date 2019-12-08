package br.com.devcave.feignbuilder.client

import br.com.devcave.feignbuilder.domain.SomeResponse
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

interface SomeHttpClient {

    @GetMapping
    fun getSomething(
        @RequestHeader headers: Map<String, String> = emptyMap(),
        @SpringQueryMap @ModelAttribute customObject: Any
    ): List<SomeResponse>?

    @PostMapping
    fun postSomething(
        @RequestHeader headers: Map<String, String> = emptyMap(),
        @RequestBody customObject: Any
    ): List<SomeResponse>?
}