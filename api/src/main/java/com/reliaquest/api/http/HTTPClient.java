package com.reliaquest.api.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.ResponseEmployeeWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * HTTP Client to invoke Employee APIs running on Server Application.
 */
@Slf4j
@Component
public class HTTPClient {
    private static final String BASE_URL = "http://localhost:8112/api/v1/employee";

    @Autowired
    private RestTemplate restTemplate;

    public List<Employee> getAllEmployees() {
        log.debug("Getting list of all employees.");
        AtomicReference<ResponseEmployeeWrapper<List<Employee>>> ref = new AtomicReference<>();
        executeRequest(BASE_URL, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseEmployeeWrapper<List<Employee>>>() {}, ref);
        return ref.get().getData();
    }

    public Employee createEmployee(Employee employee) {
        log.debug("Creating employee: {}.", employee);
        AtomicReference<ResponseEmployeeWrapper<Employee>> ref = new AtomicReference<>();
        executeRequest(BASE_URL, HttpMethod.POST, employee, new ParameterizedTypeReference<ResponseEmployeeWrapper<Employee>>() {}, ref);
        return ref.get().getData();
    }

    public Employee getEmployee(String id) {
        log.debug("Getting employee by id: {}.", id);
        AtomicReference<ResponseEmployeeWrapper<Employee>> ref = new AtomicReference<>();
        String url = BASE_URL.concat("/").concat(id);
        executeRequest(url, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseEmployeeWrapper<Employee>>() {}, ref);
        return ref.get().getData();
    }

    public Boolean deleteEmployee(String name) {
        log.debug("Deleting employee by name: {}.", name);
        AtomicReference<ResponseEmployeeWrapper<Boolean>> ref = new AtomicReference<>();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode deleteEmployeeJson = objectMapper.createObjectNode();
        deleteEmployeeJson.put("name", name);
        String deleteEmployeeJsonString = deleteEmployeeJson.toString();
        executeRequest(BASE_URL, HttpMethod.DELETE, deleteEmployeeJsonString, new ParameterizedTypeReference<ResponseEmployeeWrapper<Boolean>>() {}, ref);
        return ref.get().getData();
    }

    /**
     * This method executes http request using given parameters along with rate limit handling.
     * Retries http request after 15 seconds if status code is 429.
     * @param url
     * @param httpMethod
     * @param payload
     * @param typeReference
     * @param ref
     */
    private void executeRequest(String url, HttpMethod httpMethod, Object payload, ParameterizedTypeReference typeReference, AtomicReference ref) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        HttpEntity<Object> requestEntity;
        if (payload != null) {
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            requestEntity = new HttpEntity<>(payload, httpHeaders);
        } else {
            requestEntity = new HttpEntity<>(httpHeaders);
        }
        ResponseEntity responseEntity = restTemplate.exchange(url, httpMethod, requestEntity, typeReference);
        log.debug("Received response status: {}.", responseEntity.getStatusCode());
        if (HttpStatus.TOO_MANY_REQUESTS.equals(responseEntity.getStatusCode())) {
            try {
                log.debug("Retrying request.");
                TimeUnit.SECONDS.sleep(15);
                executeRequest(url, httpMethod, payload, typeReference, ref);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        } else {
            ref.set(responseEntity.getBody());
        }
    }
}
