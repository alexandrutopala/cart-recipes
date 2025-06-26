package com.cartrecipes.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.UUID;

@Configuration
@Log4j2
public class H2Config {
    @Bean
    public DataSource dataSource() {
        var dbName = UUID.randomUUID().toString();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:" + dbName + ";DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        log.info("H2 in-memory database initialized with name: {}", dbName);

        return dataSource;
    }
}
