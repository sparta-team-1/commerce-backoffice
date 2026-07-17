package org.example.commercebackoffice.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(new Info()
                .title("Commerce Back-Office API")
                .version("1.0")
                .description("Commerce Back-Office API")
        );
    }
}
