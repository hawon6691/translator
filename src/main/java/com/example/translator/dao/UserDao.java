package com.example.translator.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.translator.dto.User;

import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

@Repository
public class UserDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations insertUser;

    public UserDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("`user`")
                .usingGeneratedKeyColumns("user_id");
    }

    @Transactional
    public User addUser(String email, String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreated_at(LocalDateTime.now());
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        Number number = insertUser.executeAndReturnKey(params);
        Long userId = number.longValue();
        user.setUser_id(userId);
        return user;
    }
    
    @Transactional(readOnly = true)
    public User getUser(String email) {
        try {
            String sql = "select user_id, email, username, password, created_at from `user` where email = :email";
            SqlParameterSource params = new MapSqlParameterSource("email", email);
            RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
            User user = jdbcTemplate.queryForObject(sql, params, rowMapper);
            return user;
        } catch (Exception ex) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        String sql = "SELECT user_id, email, username, password, created_at FROM `user`";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));
    }
}
