CREATE TABLE IF NOT EXISTS mark
(
    mark_id bigserial NOT NULL ,
    unique_brand_code varchar(128) NOT NULL,
    CONSTRAINT mark_pkey PRIMARY KEY (mark_id),
    CONSTRAINT mark_unique_brand_code_key UNIQUE (unique_brand_code)
);

CREATE TABLE IF NOT EXISTS upd_document
(
    upd_id bigserial NOT NULL,
    transaction_id varchar(64) NOT NULL,
    upd_date timestamp without time zone NOT NULL,
    upd_sender_code varchar(64) NOT NULL,
    upd_recipient_code varchar(64) NOT NULL,
    CONSTRAINT upd_document_pkey PRIMARY KEY (upd_id),
    CONSTRAINT upd_document_transaction_id_key UNIQUE (transaction_id)
);

CREATE TABLE IF NOT EXISTS upd_document_detail
(
    upd_detail_id bigserial NOT NULL,
    line_number integer NOT NULL,
    product_name varchar(128) NOT NULL,
    upd_doc_id bigint NOT NULL,
    CONSTRAINT upd_document_detail_pkey PRIMARY KEY (upd_detail_id),
	FOREIGN KEY (upd_doc_id) REFERENCES upd_document (upd_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS upd_document_mark_relation
(
    udmr_id bigserial NOT NULL,
    mark_id bigint NOT NULL,
    upd_document_detail_id bigint NOT NULL,
    CONSTRAINT upd_document_mark_relation_pkey PRIMARY KEY (udmr_id),
	FOREIGN KEY (mark_id) REFERENCES mark (mark_id) ON DELETE CASCADE,
	FOREIGN KEY (upd_document_detail_id) REFERENCES upd_document_detail (upd_detail_id) ON DELETE CASCADE
);