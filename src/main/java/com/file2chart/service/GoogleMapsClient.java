package com.file2chart.service;

import com.file2chart.config.rest.ApiKeysConfig;
import com.file2chart.model.dto.local.Coordinate;
import com.file2chart.model.dto.local.GeoLocation;
import com.file2chart.model.dto.output.MapOutput;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@Service
public class GoogleMapsClient {

    private final RestTemplate restTemplate;
    private final ApiKeysConfig apiKeysConfig;
    private String script;

    private static String API_BASE_URL = "https://maps.googleapis.com/maps/api";
    private static String API_SCRIPT_URL = API_BASE_URL + "/js";
    private static String API_STATIC_URL = API_BASE_URL + "/staticmap";
    private static String API_KEY_PARAMETER = "?key=";

    public GoogleMapsClient(RestTemplate restTemplate, ApiKeysConfig apiKeysConfig) {
        this.restTemplate = restTemplate;
        this.apiKeysConfig = apiKeysConfig;
    }

    @PostConstruct
    public void init() {
        String scriptUrl = API_SCRIPT_URL + API_KEY_PARAMETER + apiKeysConfig.getGoogle() + "&callback=initMap&libraries=marker";
        ResponseEntity<String> response = restTemplate.getForEntity(scriptUrl, String.class);
        this.script = response.getBody();
    }

    public String getScript() {
        return script;
    }

    public InputStreamResource getImage(MapOutput mapOutput) {
        String size = "1920x1080";
        StringBuilder markers = new StringBuilder();

        List<GeoLocation> geoLocations = mapOutput.getGeoLocations();
        for (GeoLocation geoLocation : geoLocations) {
            Coordinate coordinate = geoLocation.getCoordinate();
            double lat = Double.parseDouble(coordinate.getLatitude());
            double lng = Double.parseDouble(coordinate.getLongitude());
            markers.append(lat).append(",").append(lng).append("|");
        }

        String staticMapURL = String.format(API_STATIC_URL + API_KEY_PARAMETER + apiKeysConfig.getGoogle() + "&size=%s&markers=%s", size, markers);

        ResponseEntity<byte[]> response = restTemplate.exchange(staticMapURL, HttpMethod.GET, null, byte[].class);
        InputStream inputStream = new ByteArrayInputStream(response.getBody());

        return new InputStreamResource(inputStream);
    }
}
