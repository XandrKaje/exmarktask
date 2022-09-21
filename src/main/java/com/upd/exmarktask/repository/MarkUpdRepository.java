package com.upd.exmarktask.repository;


import com.upd.exmarktask.model.dto.DocumentUpdDto;
import com.upd.exmarktask.model.exception.MarkException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
public class MarkUpdRepository {

    private final JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate settingJdbcTemplate() {
        return new NamedParameterJdbcTemplate(requireNonNull(jdbcTemplate.getDataSource()));
    }

    private static final String MARK_ID = "mark_id";
    private static final String UNIQUE_BRAND_CODE = "unique_brand_code";

    public List<DocumentUpdDto.DetailDto.MarkDto> showAll() {
        return jdbcTemplate.query("SELECT mark_id, unique_brand_code FROM mark", new DocumentUpdDto.DetailDto.MarkDto());
    }

    public DocumentUpdDto.DetailDto.MarkDto showById(Long id) {
        var param = new MapSqlParameterSource();
        param.addValue(MARK_ID, id);
        try {
            return settingJdbcTemplate().queryForObject("SELECT mark_id, unique_brand_code FROM mark WHERE mark_id = :mark_id", param, new DocumentUpdDto.DetailDto.MarkDto());
        } catch (EmptyResultDataAccessException e) {
            throw new MarkException(format("Марка не былы найдена по id = %d", id));
        }
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM mark WHERE mark_id= ?", id);
    }

    public Long createMark(DocumentUpdDto.DetailDto.MarkDto mark) {
        var simpleJdbcInsertMark = new SimpleJdbcInsert(requireNonNull(jdbcTemplate.getDataSource()))
                .withTableName("mark")
                .usingGeneratedKeyColumns(MARK_ID);
        var parameters = new HashMap<String, Object>();
        parameters.put(UNIQUE_BRAND_CODE, mark.getUniqueBrandCode());
        var id = simpleJdbcInsertMark.executeAndReturnKey(parameters);
        return id.longValue();
    }
}
