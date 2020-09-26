package com.netcracker.edu.distancestudyweb.configuration;

import com.netcracker.edu.distancestudyweb.service.impl.auth.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }
}
