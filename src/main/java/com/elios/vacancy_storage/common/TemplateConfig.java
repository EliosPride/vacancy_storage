package com.elios.vacancy_storage.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TemplateConfig {

    @Bean
    public RestTemplate storageClientRestTemplate() {
        return new RestTemplate();
    }
}
