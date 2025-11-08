package com.example.translator.dao;

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

import com.example.translator.dto.Language;

@Repository
public class LanguageDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations insertLanguage;

    public LanguageDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertLanguage = new SimpleJdbcInsert(dataSource)
                .withTableName("`language`");
    }

    @Transactional
    public Language addLanguage(String langCode, String langName) {
        Language language = new Language();
        language.setLang_code(langCode);
        language.setLang_name(langName);

        SqlParameterSource params = new BeanPropertySqlParameterSource(language);
        insertLanguage.execute(params);

        return language;
    }

    @Transactional(readOnly = true)
    public Language getLanguage(String langCode) {
        try {
            String sql = "SELECT lang_code, lang_name FROM `language` WHERE lang_code = :langCode";
            SqlParameterSource params = new MapSqlParameterSource("langCode", langCode);
            RowMapper<Language> rowMapper = BeanPropertyRowMapper.newInstance(Language.class);
            return jdbcTemplate.queryForObject(sql, params, rowMapper);
        } catch (Exception ex) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Language> getAllLanguages() {
        String sql = "SELECT lang_code, lang_name FROM `language`";
        RowMapper<Language> rowMapper = BeanPropertyRowMapper.newInstance(Language.class);
        return jdbcTemplate.query(sql, rowMapper);
    }
}
