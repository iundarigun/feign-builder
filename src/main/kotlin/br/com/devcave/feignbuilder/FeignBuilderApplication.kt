package br.com.devcave.feignbuilder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.context.annotation.Import

@EnableCaching
@SpringBootApplication
class FeignBuilderApplication

fun main(args: Array<String>) {
	runApplication<FeignBuilderApplication>(*args)
}
