package com.example.translator.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.translator.dto.Folder;

@Repository
public class FolderDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations insertFolder;

    public FolderDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertFolder = new SimpleJdbcInsert(dataSource)
                .withTableName("`folder`")
                .usingGeneratedKeyColumns("folder_id");
    }

    @Transactional
    public Folder addFolder(Long userId, String folderName) {
        Folder folder = new Folder();
        folder.setUser_id(userId);
        folder.setFolder_name(folderName);
        folder.setCreated_at(LocalDateTime.now());

        SqlParameterSource params = new BeanPropertySqlParameterSource(folder);
        Number key = insertFolder.executeAndReturnKey(params);
        folder.setFolder_id(key.longValue());

        return folder;
    }

    @Transactional(readOnly = true)
    public Folder getFolder(Long folderId) {
        try {
            String sql = "SELECT folder_id, user_id, folder_name, created_at " +
                         "FROM `folder` WHERE folder_id = :folderId";
            SqlParameterSource params = new MapSqlParameterSource("folderId", folderId);
            RowMapper<Folder> rowMapper = BeanPropertyRowMapper.newInstance(Folder.class);
            return jdbcTemplate.queryForObject(sql, params, rowMapper);
        } catch (Exception ex) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Folder> getFoldersByUser(Long userId) {
        String sql = "SELECT folder_id, user_id, folder_name, created_at " +
                     "FROM `folder` WHERE user_id = :userId ORDER BY created_at DESC";
        SqlParameterSource params = new MapSqlParameterSource("userId", userId);
        RowMapper<Folder> rowMapper = BeanPropertyRowMapper.newInstance(Folder.class);
        return jdbcTemplate.query(sql, params, rowMapper);
    }

    @Transactional(readOnly = true)
    public List<Folder> getAllFolders() {
        String sql = "SELECT folder_id, user_id, folder_name, created_at FROM `folder`";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Folder.class));
    }
}
