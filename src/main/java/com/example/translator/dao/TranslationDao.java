package com.example.translator.dao;

import java.time.LocalDateTime;

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

import com.example.translator.dto.Translation;

@Repository
public class TranslationDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations insertTranslation;

    public TranslationDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertTranslation = new SimpleJdbcInsert(dataSource)
                .withTableName("`translation`")
                .usingGeneratedKeyColumns("translation_id");
    }

    @Transactional
    public Translation addTranslation(Long folder_id, String source_lang, String target_lang, String original_text,
            String translated_text, Long file_id) {
        Translation translation = new Translation();
        translation.setFolder_id(folder_id);
        translation.setSource_lang(source_lang);
        translation.setTarget_lang(target_lang);
        translation.setOriginal_text(original_text);
        translation.setTranslated_text(translated_text);
        translation.setFile_id(file_id);
        translation.setCreated_at(LocalDateTime.now());

        SqlParameterSource params = new BeanPropertySqlParameterSource(translation);
        Number generatedId = insertTranslation.executeAndReturnKey(params);
        translation.setTranslation_id(generatedId.longValue());

        return translation;
    }

    @Transactional(readOnly = true)
    public Translation getTranslation(Long translationId) {
        try {
            String sql = "SELECT translation_id, folder_id, source_lang, target_lang, " +
                    "original_text, translated_text, file_id, created_at " +
                    "FROM `translation` WHERE translation_id = :translationId";
            SqlParameterSource params = new MapSqlParameterSource("translationId", translationId);
            RowMapper<Translation> rowMapper = BeanPropertyRowMapper.newInstance(Translation.class);
            return jdbcTemplate.queryForObject(sql, params, rowMapper);
        } catch (Exception ex) {
            return null;
        }
    }
}
