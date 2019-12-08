# Feign Builder

This repo is a sample to test Feign like a RestTemplate, creating dynamically for different URLs.

## Keys

#### FeignConfiguration
This class contains the common configuration for feign, like decoder, encoder, retries and contract. Its are important for interpreting the client

#### SomeHttpClient
This interface is a feign interface without the @FeignClient annotation.

#### ClientService
This service has a method that builds the client with the URL parameter. This method is cacheable to avoid creating the same client some times in a few minutes

## Testing
I use https://github.com/iundarigun/mockws to test requests and response, and the swagger exposed at port 8080 on this project.
