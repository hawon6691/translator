package com.example.translator.service;

import com.example.translator.exception.DuplicateResourceException;
import com.example.translator.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.translator.dao.UserDao;
import com.example.translator.dto.User;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User addUser(String email, String username, String password) {
        // 이메일 중복 확인
        User existingUser = userDao.getUser(email);
        if (existingUser != null) {
            throw new DuplicateResourceException("이미 가입된 이메일입니다: " + email);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        log.info("Creating new user: {}", email);
        User user = userDao.addUser(email, username, encodedPassword);
        return user;
    }

    @Transactional(readOnly = true)
    public User getUser(String email) {
        User user = userDao.getUser(email);
        if (user == null) {
            throw new ResourceNotFoundException("사용자를 찾을 수 없습니다: " + email);
        }
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public boolean validatePassword(String email, String rawPassword) {
        User user = getUser(email);
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}