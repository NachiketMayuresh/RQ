package com.reliaquest.api.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * RestTemplate error handler.
 */
@Slf4j
public class HTTPResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is5xxServerError() ||
                response.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is5xxServerError()) {
            //throw exception
            log.error("Handling server error: {}.", response.getStatusCode());
        } else if (response.getStatusCode().is4xxClientError()) {
            if (!HttpStatus.TOO_MANY_REQUESTS.equals(response.getStatusCode())) {
                //throw exception
                log.error("Handling client error: {}.", response.getStatusCode());
            }
        }
    }
}
