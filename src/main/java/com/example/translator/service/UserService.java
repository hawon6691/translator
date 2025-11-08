package com.example.translator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.translator.dao.UserDao;
import com.example.translator.dto.User;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserDao userDao;

    @Transactional
    public User addUser(String email, String username, String password) {
        User user1 = getUser(email);
        if (user1 != null) {
            throw new RuntimeException("이미 가입된 이메일입니다.");
        }

        User user = userDao.addUser(email, username, password);
        return user;
    }

    @Transactional
    public User getUser(String email) {
        return userDao.getUser(email);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        // UserDao에 getAllUsers() 필요
        return userDao.getAllUsers();
    }
}
