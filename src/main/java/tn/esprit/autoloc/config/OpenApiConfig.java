package tn.esprit.autoloc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI autolocOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AutoLoc REST API")
                        .description("API REST pour la gestion d'agence de location de véhicules AutoLoc - ESPRIT 4ème Année")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Molka Jebali")
                                .email("molka.jebali@esprit.tn"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}
