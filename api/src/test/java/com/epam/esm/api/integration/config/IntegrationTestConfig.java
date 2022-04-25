package com.epam.esm.api.integration.config;

import com.epam.esm.core.configuration.TestConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.sql.DataSource;

@PropertySource("classpath:properties/integration.properties")
public class IntegrationTestConfig extends TestConfig {

//    @Value("${dbScript}")
    @Value("classpath:scripts/schema.sql")
    private String dbScript;

//    @Value("${dbName}")
    @Value("gift_certificate_system_test")
    private String dbName;

    @Bean
    @Override
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName(dbName)
                .addScript(dbScript)
                .build();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
