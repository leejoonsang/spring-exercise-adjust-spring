package com.exercise.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDaoFactory {
    @Bean
    public UserDao awsUserDao() {
        AWSConnectionMaker awsConnectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao(awsConnectionMaker);
        return userDao;
    }

//    public UserDao localUserDao() {
//        UserDao userDao = new UserDao(new LocalConnectionMaker());
//        return userDao;
//    }

}
