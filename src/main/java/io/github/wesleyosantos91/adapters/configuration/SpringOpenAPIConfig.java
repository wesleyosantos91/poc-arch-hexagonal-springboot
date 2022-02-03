package io.github.wesleyosantos91.adapters.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringOpenAPIConfig {


    @Bean
    public OpenAPI springOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("POC - Ports & Adapters Architecture ou Arquitetura Hexagonal")
                        .version("0.0.1")
                        .description("Projeto aplicando Arquitetura Hexagonal")
                        .termsOfService("http://www.termsofservice.url")
                        .contact(descriptionContact())
                        .license(descriptionLicense())
                );
    }

    private Contact descriptionContact() {
        return new Contact()
                .name("Wesley Oliveira Santos")
                .email("wesleyosantos91@gmail.com")
                .url("https://wesleyosantos91.github.io/");
    }

    private License descriptionLicense() {
        return new License()
                .name("License")
                .url("http://www.license.url");
    }


}
