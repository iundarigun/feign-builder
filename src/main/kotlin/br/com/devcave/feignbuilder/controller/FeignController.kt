package br.com.devcave.feignbuilder.controller

import br.com.devcave.feignbuilder.domain.SomeRequest
import br.com.devcave.feignbuilder.service.FeignService
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("feign")
class FeignController(private val feignService: FeignService) {

    @GetMapping
    fun getUsingGet(
        @RequestParam url: String,
        @RequestParam(required = false) id: Long?,
        @RequestParam name: String
    ): HttpEntity<Any?> {
        val list = feignService.getSomeResponseByGet(url, SomeRequest(id, name))

        if (list != null){
            return ResponseEntity.ok(list)
        }

        return ResponseEntity.ok().build()
    }

    @PostMapping
    fun getUsingPost(
        @RequestParam url: String,
        @RequestParam(required = false) id: Long?,
        @RequestParam name: String
    ): HttpEntity<Any?> {
        val list = feignService.getSomeResponseByPost(url, SomeRequest(id, name))

        if (list != null){
            return ResponseEntity.ok(list)
        }

        return ResponseEntity.ok().build()
    }
}