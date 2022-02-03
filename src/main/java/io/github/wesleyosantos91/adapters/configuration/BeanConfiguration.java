package io.github.wesleyosantos91.adapters.configuration;

import io.github.wesleyosantos91.Application;
import io.github.wesleyosantos91.application.core.service.PersonServiceImpl;
import io.github.wesleyosantos91.application.ports.PersonRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class BeanConfiguration {

    @Bean
    PersonServiceImpl personServiceImpl(PersonRepositoryPort repositoryPort) {
        return new PersonServiceImpl(repositoryPort);
    }
}
