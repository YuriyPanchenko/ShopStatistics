package com.example.shop.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;


@Configuration
public class DbConnectionConfig {

    @Value("${spring.datasource.url}")
    private String dataSource;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;


    @Bean
    public DataSource dataSource() {
        try {

            URI jdbUri = new URI(System.getenv("JAWSDB_URL"));

            String username = jdbUri.getUserInfo().split(":")[0];
            String password = jdbUri.getUserInfo().split(":")[1];
            String port = String.valueOf(jdbUri.getPort());
            String jdbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();

            return DataSourceBuilder.create()
                    .url(jdbUrl)
                    .username(username)
                    .password(password)
                    .build();

        } catch (Exception e) {
            return DataSourceBuilder.create()
                    .url(dataSource)
                    .username(userName)
                    .password(password)
                    .build();
        }
    }

}
