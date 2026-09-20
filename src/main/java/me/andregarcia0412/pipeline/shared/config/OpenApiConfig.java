package me.andregarcia0412.pipeline.shared.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title="Platinado API",
                version = "1.0.0"
        )
)
@Configuration
public class OpenApiConfig {
}
