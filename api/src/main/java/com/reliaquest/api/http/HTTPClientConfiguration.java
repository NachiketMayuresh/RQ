package com.reliaquest.api.http;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate configuration.
 */
@Configuration
public class HTTPClientConfiguration {

    @Bean
    public RestTemplate getRestClient() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.errorHandler(new HTTPResponseErrorHandler()).build();
        return restTemplate;
    }
}
