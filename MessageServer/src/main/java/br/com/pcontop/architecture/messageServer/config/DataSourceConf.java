package br.com.pcontop.architecture.messageServer.config;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Created by pcont_000 on 26/01/2016.
 */
//@Configuration
public class DataSourceConf {
//    @Bean
    public DataSource dataSource(Environment environment) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/remote_compiler");
        dataSource.setUsername("logger");
        dataSource.setPassword("1234");
        return dataSource;
    }
}
