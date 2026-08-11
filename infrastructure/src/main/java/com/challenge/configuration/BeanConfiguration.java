package com.challenge.configuration;

import com.challenge.ManageProductUseCase;
import com.challenge.ports.in.ProductUCPort;
import com.challenge.ports.out.ProductDBPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ProductUCPort productUCPort(ProductDBPort databasePort) {
        return new ManageProductUseCase(databasePort);
    }
}