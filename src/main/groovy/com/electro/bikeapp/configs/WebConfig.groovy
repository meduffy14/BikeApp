package com.electro.bikeapp.configs

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@SuppressWarnings(['DuplicateStringLiteral'])
class WebConfig implements WebMvcConfigurer {

    @Override
    void addCorsMappings(CorsRegistry registry) {
        registry.addMapping('/**')
                .allowedMethods('HEAD', 'GET', 'PUT', 'POST', 'DELETE', 'PATCH')
                .allowedHeaders('HEAD', 'GET', 'PUT', 'POST', 'DELETE', 'PATCH')
    }

}
