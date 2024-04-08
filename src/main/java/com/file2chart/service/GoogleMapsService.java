package com.file2chart.service;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class GoogleMapsService {

    private final RestTemplate restTemplate;
    private String script;
    private String image;

    public GoogleMapsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void init() {
        String scriptUrl = "https://maps.googleapis.com/maps/api/js?key=" + "AIzaSyAO6GYjHFy42ZkJN3cM0nxtNEBKrkjwrBA" + "&callback=initMap";
        ResponseEntity<String> response = restTemplate.getForEntity(scriptUrl, String.class);
        this.script = response.getBody();
    }

    public String getScript() {
        return script;
    }

    public InputStreamResource getImage() {
        String center = "51.5074,0.1278";
        int zoom = 10;
        String size = "600x300";
        String apiKey = "AIzaSyAO6GYjHFy42ZkJN3cM0nxtNEBKrkjwrBA";

        String staticMapURL = "https://maps.googleapis.com/maps/api/staticmap?center=" + center + "&zoom=" + zoom +
                "&size=" + size + "&format=png&key=" + apiKey;

        ResponseEntity<byte[]> response = restTemplate.exchange(staticMapURL, HttpMethod.GET, null, byte[].class);
        InputStream inputStream = new ByteArrayInputStream(response.getBody());

        return new InputStreamResource(inputStream);
    }
}
