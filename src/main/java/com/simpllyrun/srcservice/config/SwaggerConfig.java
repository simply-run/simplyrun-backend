package com.croquiscom.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi jwtApi() {
        String[] paths = {"/api/**"};
        return GroupedOpenApi.builder()
                .group("휴가 신청 API")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("휴가 신청 API 문서")
                        .description("테스트 시 사용자 ID : user / 사용자 PW : 1234 사용")
                        .version("v0.0.1"));
    }
}