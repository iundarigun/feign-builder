package br.com.devcave.feignbuilder.config

import feign.Client
import feign.Contract
import feign.Feign
import feign.Request
import feign.Retryer
import feign.codec.Decoder
import feign.codec.Encoder
import feign.optionals.OptionalDecoder
import org.springframework.beans.factory.ObjectFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder
import org.springframework.cloud.openfeign.support.SpringDecoder
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.cloud.openfeign.support.SpringMvcContract
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfiguration(
    private val messageConverters: ObjectFactory<HttpMessageConverters>,
    @Value("\${feign-builder.client.readTimeout:10000}")
    private val readTimeout: Int,
    @Value("\${feign-builder.client.connectionTimeout:10000}")
    private val connectionTimeout: Int
    ) {

    fun feignDecoder(): Decoder {
        return OptionalDecoder(
            ResponseEntityDecoder(SpringDecoder(this.messageConverters))
        )
    }

    fun feignEncoder(): Encoder {
        return SpringEncoder(this.messageConverters)
    }

    fun feignContract(): Contract {
        return SpringMvcContract()
    }

    @Bean
    fun feignBuilder(): Feign.Builder {
        return Feign.builder()
            .options(Request.Options(connectionTimeout,readTimeout))
            .retryer(Retryer.NEVER_RETRY)
            .contract(feignContract())
            .decoder(feignDecoder())
            .encoder(feignEncoder())
    }
}