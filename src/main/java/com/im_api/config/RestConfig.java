package com.im_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {
    @Bean
    public RestTemplate restTemplate(
            @Value("${nominatim.user-agent}") String userAgent
    ) {
        RestTemplate rt = new RestTemplate();
        rt.getInterceptors().add((request, body, exec) -> {
            request.getHeaders().set("User-Agent", userAgent);
            return exec.execute(request, body);
        });
        return rt;
    }
}