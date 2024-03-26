package com.file2chart.service;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleMapsService {

    private final RestTemplate restTemplate;
    private String script;

    public GoogleMapsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void init() {
        String url = "https://maps.googleapis.com/maps/api/js?key=" + "AIzaSyAO6GYjHFy42ZkJN3cM0nxtNEBKrkjwrBA" + "&callback=initMap";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        this.script = response.getBody();
    }

    public String getScript() {
        return script;
    }
}
