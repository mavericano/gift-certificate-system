package com.epam.esm.core.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Arrays;
import java.util.Locale;

@Profile("prod")
@Configuration
@ComponentScan(basePackages = "com.epam.esm")
@PropertySource("classpath:properties/db.properties")
@EnableWebMvc
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class ProdConfig implements WebMvcConfigurer {

    @Value("${driverClass}")
    private String driverClass;

    @Value("${dbUser}")
    private String user;

    @Value("${password}")
    private String password;

    @Value("${jdbcUrl}")
    private String jdbcUrl;


    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(driverClass);
            dataSource.setJdbcUrl(jdbcUrl);
            dataSource.setUser(user);
            dataSource.setPassword(password);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

//    @Bean
//    public LocaleResolver localeResolver() {
//        final AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
//        resolver.setSupportedLocales(Arrays.asList(new Locale("ru"), new Locale("en")));
//        resolver.setDefaultLocale(new Locale("en"));
//        return resolver;
//    }
}
