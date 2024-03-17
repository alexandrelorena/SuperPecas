package br.com.masterclass.superpecas.config;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public GroupedOpenApi customOpenAPI() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public SwaggerUiConfigParameters swaggerUiConfigParameters() {
        SwaggerUiConfigParameters parameters = new SwaggerUiConfigParameters();
        parameters.setDefaultModelsExpandDepth(-1);
        parameters.setDefaultModelExpandDepth(3);
        parameters.setDefaultModelRendering("example");
        parameters.setDisplayOperationId(true);
        parameters.setDisplayRequestDuration(false);
        parameters.setFilter(true);
        parameters.setMaxDisplayedTags(null);
        parameters.setShowExtensions(true);
        parameters.setDeepLinking(true);
        parameters.setTagsSorter("alpha");
        parameters.setOperationsSorter("alpha");
        return parameters;
    }
}
