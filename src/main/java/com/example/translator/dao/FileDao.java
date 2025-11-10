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

import com.example.translator.dto.File;

@Repository
public class FileDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations insertFile;

    public FileDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertFile = new SimpleJdbcInsert(dataSource)
                .withTableName("`file`")
                .usingGeneratedKeyColumns("file_id");
    }

    @Transactional
    public File addFile(String fileName, String filePath, String fileType) {
        File file = new File();
        file.setFile_name(fileName);
        file.setFile_path(filePath);
        file.setFile_type(fileType);
        file.setUploaded_at(LocalDateTime.now());

        SqlParameterSource params = new BeanPropertySqlParameterSource(file);
        Number key = insertFile.executeAndReturnKey(params);
        file.setFile_id(key.longValue());

        return file;
    }

    @Transactional(readOnly = true)
    public File getFile(Long fileId) {
        try {
            String sql = "SELECT file_id, file_name, file_path, file_type, uploaded_at " +
                    "FROM `file` WHERE file_id = :fileId";
            SqlParameterSource params = new MapSqlParameterSource("fileId", fileId);
            RowMapper<File> rowMapper = BeanPropertyRowMapper.newInstance(File.class);
            return jdbcTemplate.queryForObject(sql, params, rowMapper);
        } catch (Exception ex) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<File> getAllFiles() {
        String sql = "SELECT file_id, file_name, file_path, file_type, uploaded_at " +
                "FROM `file` ORDER BY uploaded_at DESC";
        RowMapper<File> rowMapper = BeanPropertyRowMapper.newInstance(File.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Transactional
    public void deleteFile(Long fileId) {
        String sql = "DELETE FROM `file` WHERE file_id = :fileId";
        SqlParameterSource params = new MapSqlParameterSource("fileId", fileId);
        jdbcTemplate.update(sql, params);
    }
}