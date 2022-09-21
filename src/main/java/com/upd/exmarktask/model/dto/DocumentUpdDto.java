package com.upd.exmarktask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DocumentUpdDto implements RowMapper<DocumentUpdDto> {
    private Long id;
    private String transactionId;
    private Timestamp date;
    private String recipientCode;
    private String senderCode;
    private List<DetailDto> details;

    private static final String UPD_ID = "upd_id";
    private static final String TRANSACTION_ID = "transaction_id";
    private static final String UPD_DATE = "upd_date";
    private static final String UPD_SENDER_CODE = "upd_sender_code";
    private static final String UPD_RECIPIENT_CODE = "upd_recipient_code";

    @Override
    public DocumentUpdDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return DocumentUpdDto.builder()
                .id(rs.getLong(UPD_ID))
                .transactionId(rs.getString(TRANSACTION_ID))
                .date(rs.getTimestamp(UPD_DATE))
                .recipientCode(rs.getString(UPD_RECIPIENT_CODE))
                .senderCode(rs.getString(UPD_SENDER_CODE))
                .build();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class DetailDto {
        private Long id;

        private Long docId;

        private Integer lineNumber;

        private String productName;

        private List<MarkDto> marks;

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        @Builder
        public static class MarkDto implements RowMapper<MarkDto> {
            private Long markId;
            private String uniqueBrandCode;


            private static final String MARK_ID = "mark_id";
            private static final String UNIQUE_BRAND_CODE = "unique_brand_code";

            @Override
            public MarkDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return MarkDto.builder()
                        .markId(rs.getLong(MARK_ID))
                        .uniqueBrandCode(rs.getString(UNIQUE_BRAND_CODE))
                        .build();
            }
        }
    }
}
