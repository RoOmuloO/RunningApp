package com.example.romuloroger.runningapp.http;

import android.content.Context;

import com.example.romuloroger.runningapp.utils.Preferencias;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class HttpService<T, K> {

    private final String apiUrl = "https://api.davesmartins.com.br/api/";
    private final Class<T> type;
    private String resource;
    private Context context;


    public HttpService(String resource, Context context, Class<T> type) {
        this.resource = resource;
        this.type = type;
        this.context = context;
    }

    public List<T> getAll(String action, Class<T[]> type) throws HttpClientErrorException {
        RestTemplate restTemplate = this.getRestTemplate();
        HttpEntity<T> entity = (HttpEntity<T>) this.configurarHttpEntity(null);
        ResponseEntity<T[]> response = restTemplate.exchange(apiUrl + resource + action,HttpMethod.GET,entity,type);
        return Arrays.asList(response.getBody());
    }

    public T getById(String action) throws HttpClientErrorException {
        RestTemplate restTemplate = this.getRestTemplate();
        HttpEntity<T> entity = (HttpEntity<T>) this.configurarHttpEntity(null);
        T response = restTemplate.getForObject(apiUrl + resource + action, type);
        return response;
    }


    public T post(String action, K body) throws HttpClientErrorException {
        RestTemplate restTemplate = this.getRestTemplate();
        HttpEntity<K> entity = (HttpEntity<K>) this.configurarHttpEntity(body);
        T response = restTemplate.postForObject(apiUrl + resource + action, entity, type);
        return response;
    }

    public T put(String action, K data) throws HttpClientErrorException {
        RestTemplate restTemplate = this.getRestTemplate();
        HttpEntity<K> entity = (HttpEntity<K>) this.configurarHttpEntity(data);
        ResponseEntity<T> response = restTemplate.exchange(apiUrl + resource + action, HttpMethod.PUT, entity, type);
        return response.getBody();
    }

    public void delete(String action) throws HttpClientErrorException {
        RestTemplate restTemplate = this.getRestTemplate();
        HttpEntity<T> entity = (HttpEntity<T>) this.configurarHttpEntity(null);
        restTemplate.delete(apiUrl + resource + action, entity);
    }

    private HttpEntity<K> configurarHttpEntity(K data) {
        HttpHeaders header = new HttpHeaders();
        header.set("code", Preferencias.getToken(this.context));
        HttpEntity<K> entity = new HttpEntity<>(data, header);
        return entity;
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        return restTemplate;
    }


}
