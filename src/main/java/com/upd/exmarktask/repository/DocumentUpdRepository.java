package com.upd.exmarktask.repository;

import com.upd.exmarktask.model.dto.DocumentUpdDto;
import com.upd.exmarktask.model.exception.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
public class DocumentUpdRepository {

    private final JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate settingJdbcTemplate() {
        return new NamedParameterJdbcTemplate(requireNonNull(jdbcTemplate.getDataSource()));
    }

    private static final String UPD_ID = "upd_id";
    private static final String UPD_DETAIL_ID = "upd_detail_id";
    private static final String UPD_DOC_ID = "upd_doc_id";
    private static final String TRANSACTION_ID = "transaction_id";
    private static final String UPD_DATE = "upd_date";
    private static final String UPD_SENDER_CODE = "upd_sender_code";
    private static final String UPD_RECIPIENT_CODE = "upd_recipient_code";
    private static final String LINE_NUMBER = "line_number";
    private static final String PRODUCT_NAME = "product_name";
    private static final String UPD_DOCUMENT_DETAIL_ID = "upd_document_detail_id";
    private static final String MARK_ID = "mark_id";
    private static final String UDMR_ID = "udmr_id";

    public List<DocumentUpdDto> showAll() {
        return jdbcTemplate.query("SELECT upd_id, transaction_id, upd_date, upd_sender_code, upd_recipient_code FROM upd_document", new DocumentUpdDto());
    }

    public DocumentUpdDto showById(Long id) {
        var param = new MapSqlParameterSource();
        param.addValue(UPD_ID, id);
        try {
            return settingJdbcTemplate().queryForObject("SELECT upd_id, transaction_id, upd_date, upd_sender_code, upd_recipient_code FROM upd_document WHERE upd_id = :upd_id", param, new DocumentUpdDto());
        } catch (EmptyResultDataAccessException e) {
            throw new DocumentException(format("Документ не был найден по id = %d", id));
        }
    }

    public Long createUpdDocument(DocumentUpdDto document) {
        var simpleJdbcInsertDocument = new SimpleJdbcInsert(requireNonNull(jdbcTemplate.getDataSource()))
                .withTableName("upd_document")
                .usingGeneratedKeyColumns(UPD_ID);
        var parameters = new HashMap<String, Object>();
        parameters.put(TRANSACTION_ID, document.getTransactionId());
        parameters.put(UPD_DATE, new Timestamp(new Date().getTime()));
        parameters.put(UPD_RECIPIENT_CODE, document.getRecipientCode());
        parameters.put(UPD_SENDER_CODE, document.getSenderCode());

        var id = simpleJdbcInsertDocument.executeAndReturnKey(parameters);
        return id.longValue();
    }

    public Long createUpdDetail(DocumentUpdDto.DetailDto detail) {
        var simpleJdbcInsertDetail = new SimpleJdbcInsert(requireNonNull(jdbcTemplate.getDataSource()))
                .withTableName("upd_document_detail")
                .usingGeneratedKeyColumns(UPD_DETAIL_ID);
        var parameters = new HashMap<String, Object>();
        parameters.put(UPD_DOC_ID, detail.getDocId());
        parameters.put(LINE_NUMBER, detail.getLineNumber());
        parameters.put(PRODUCT_NAME, detail.getProductName());

        var id = simpleJdbcInsertDetail.executeAndReturnKey(parameters);
        return id.longValue();
    }

    public void createUpdDocumentRelation(Long detailId, Long markId) {
        var simpleJdbcInsertDocumentRelation = new SimpleJdbcInsert(requireNonNull(jdbcTemplate.getDataSource()))
                .withTableName("upd_document_mark_relation")
                .usingGeneratedKeyColumns(UDMR_ID);
        var parameters = new HashMap<String, Object>();
        parameters.put(MARK_ID, markId);
        parameters.put(UPD_DOCUMENT_DETAIL_ID, detailId);
        simpleJdbcInsertDocumentRelation.execute(parameters);
    }

    public void deleteDocumentById(Long id) {
        jdbcTemplate.update("DELETE FROM upd_document WHERE upd_id= ?", id);
    }
}
