package com.exercise.dao;

import com.exercise.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
//        UserDao userDao = new UserDaoFactory().awsUserDao();
        UserDao userDao = context.getBean("awsUserDao", UserDao.class);

        String id = "18";
        userDao.add(new User(id, "mark18", "eighteen"));

        User selectedUser = userDao.selectById(id);
        Assertions.assertEquals("mark18", selectedUser.getName());
        Assertions.assertEquals("eighteen", selectedUser.getPassword());
    }

}