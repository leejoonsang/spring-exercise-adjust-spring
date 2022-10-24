package com.exercise.dao;

import com.exercise.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() {
        this.userDao = context.getBean("awsUserDao", UserDao.class);
        this.user1 = new User("1", "tom1", "1234");
        this.user2 = new User("2", "tom2", "1212");
        this.user3 = new User("3", "tom3", "4321");
        System.out.println("before each");
    }

    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
        userDao = context.getBean("awsUserDao", UserDao.class);
        User user1 = new User("1", "sangjoon", "0000");

        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        User user = userDao.selectById(user1.getId());

        assertEquals(user1.getName(), user.getName());
        assertEquals(user1.getPassword(), user.getPassword());
    }
    @Test
    void count() throws SQLException, ClassNotFoundException {

        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        userDao.add(user2);
        assertEquals(2, userDao.getCount());
        userDao.add(user3);
        assertEquals(3, userDao.getCount());

    }

    @Test
    void delete() throws SQLException {
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());
    }

    @Test
    void SelectById() throws SQLException, ClassNotFoundException {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.selectById("30");
        });

    }

}