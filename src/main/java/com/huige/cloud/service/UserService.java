package com.huige.cloud.service;

import com.huige.cloud.Constant;
import com.huige.cloud.dao.UserDao;
import com.huige.cloud.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserDao userDao;

    @Bean
    private static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public int addUser(User user) {
        if (user == null) return Constant.Fail;
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        user.setState(1);
        User old = userDao.selectByUserName(user.getUsername());
        if (old == null) {
            userDao.insert(user);
            return user.getId();
        } else {
            return Constant.DUPLICATE;//已存在
        }
    }

    public User selectByUserName(String username) {
        return userDao.selectByUserName(username);
    }
}
