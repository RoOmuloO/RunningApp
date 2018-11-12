package com.example.romuloroger.runningapp.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class HttpService<T extends Object> {

    private final String apiUrl = "https://api.davesmartins.com.br/api/";
    private final Class<T> type;
    private String resource;


    public HttpService(String resource, Class<T> type) {
        this.resource = resource;
        this.type = type;
    }

    public List<T> getAll(String action, Class<T[]> type) {
        RestTemplate restTemplate = this.getRestTemplate();
        HttpEntity<T> entity = (HttpEntity<T>) this.configurarHttpEntity(null);
        T[] array = restTemplate.getForObject(apiUrl + resource + action, type);
        return Arrays.asList(array);
    }

    public T getById(String action) {
        RestTemplate restTemplate = this.getRestTemplate();
        HttpEntity<T> entity = (HttpEntity<T>) this.configurarHttpEntity(null);
        T response = restTemplate.getForObject(apiUrl + resource + action, type);
        return response;
    }


    public T post(String action, T body) {
        RestTemplate restTemplate = this.getRestTemplate();
        HttpEntity<T> entity = (HttpEntity<T>) this.configurarHttpEntity(body);
        T response = restTemplate.postForObject(apiUrl + resource + action, entity, type);
        return response;
    }

    public T put(String action, T data) {
        RestTemplate restTemplate = this.getRestTemplate();
        HttpEntity<T> entity = (HttpEntity<T>) this.configurarHttpEntity(data);
        ResponseEntity<T> response = restTemplate.exchange(apiUrl + resource + action, HttpMethod.PUT, entity, type);
        return response.getBody();
    }

    public void delete(String action) {
        RestTemplate restTemplate = this.getRestTemplate();
        HttpEntity<T> entity = (HttpEntity<T>) this.configurarHttpEntity(null);
        restTemplate.delete(apiUrl + resource + action, entity);
    }

    private HttpEntity<T> configurarHttpEntity(T data) {
        HttpHeaders header = new HttpHeaders();
        HttpEntity<T> entity = new HttpEntity<>(data, header);
        return entity;
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        return restTemplate;
    }


}
