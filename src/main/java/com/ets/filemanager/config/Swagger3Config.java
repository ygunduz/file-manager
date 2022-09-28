package com.ets.filemanager.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger3Config {

    @Bean
    public OpenAPI fileManagerOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Ets File Manager API")
                        .description("Ets File Manager API services for create delete update and read files in file system")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("https://www.etstur.com/")))
                .components(new Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .name("bearerAuth"))
                )
                .addSecurityItem(new SecurityRequirement().addList("JWT"));
    }
}
