package com.exercise.dao;

import com.mysql.cj.jdbc.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class UserDaoFactory {
    @Bean
    public UserDao awsUserDao() {
//        AWSConnectionMaker awsConnectionMaker = new AWSConnectionMaker();
//        UserDao userDao = new UserDao(awsConnectionMaker);
//        return userDao;
        return new UserDao(awsDataSource());
    }

    @Bean
    DataSource awsDataSource(){
        Map<String, String> env = System.getenv();
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl(env.get("DB_HOST"));
        dataSource.setUsername(env.get("DB_USER"));
        dataSource.setPassword(env.get("DB_PASSWORD"));
        return dataSource;
    }

}
