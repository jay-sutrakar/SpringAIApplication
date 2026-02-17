package com.ai;


import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public ToolCallbackProvider weatherTools(WeatherService weatherService) {
        try {
            String response = weatherService.getWeatherForecastByCityName("London");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MethodToolCallbackProvider.builder().toolObjects(weatherService).build();
    }
}
