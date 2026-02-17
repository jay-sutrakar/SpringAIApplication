package com.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final RestClient restClient;

    public WeatherService() {
        this.restClient = RestClient.create();
    }

    @Tool(description = "Get weather forecast for a specific cityName")
    public String getWeatherForecastByCityName(
            @ToolParam(description = "City Name") String cityName) {
        log.info("Weather request received for city: {}", cityName);
        try {
            String url = UriComponentsBuilder
                .fromUri(URI.create("http://api.weatherapi.com/v1/current.json"))
                .queryParam("key", apiKey)
                .queryParam("q", cityName)
                .build()
                .toUriString();

            log.info("Full URL being called: {}", url);
            return restClient.get()
                    .uri(url)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        String errorBody = new String(response.getBody().readAllBytes());
                        log.error("Client error from weather API | status={} | body={}",
                                response.getStatusCode(), errorBody);
                        throw new RuntimeException("Weather API client error: " + errorBody);
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                        String errorBody = new String(response.getBody().readAllBytes());
                        log.error("Server error from weather API | status={} | body={}",
                                response.getStatusCode(), errorBody);
                        throw new RuntimeException("Weather API server error: " + errorBody);
                    })
                    .body(String.class);

        } catch (Exception e) {
            log.error("Failed to get weather data for city: {}", cityName, e);
            return String.format("Failed to retrieve weather data for %s: %s",
                    cityName, e.getMessage());
        }
    }
}
