-- DELETE
    drop TABLE board_info_table cascade constraint;
    drop TABLE user_table cascade constraint;
    drop TABLE content_table cascade constraint;
    drop sequence user_seq;
    drop sequence content_seq;

COMMIT;

-- CREATE
CREATE SEQUENCE user_seq START WITH 1 INCREMENT BY 1 MINVALUE 1;

CREATE SEQUENCE content_seq START WITH 1 INCREMENT BY 1 MINVALUE 1;

CREATE TABLE board_info_table (
    board_info_idx  NUMBER
        CONSTRAINT board_info_pk PRIMARY KEY,
    board_info_name VARCHAR2(500) NOT NULL
);

INSERT INTO board_info_table (
    board_info_idx,
    board_info_name
) VALUES (
    1,
    '자유 게시판'
);

INSERT INTO board_info_table (
    board_info_idx,
    board_info_name
) VALUES (
    2,
    '유머 게시판'
);

INSERT INTO board_info_table (
    board_info_idx,
    board_info_name
) VALUES (
    3,
    '정치 게시판'
);

INSERT INTO board_info_table (
    board_info_idx,
    board_info_name
) VALUES (
    4,
    '스포츠 게시판'
);

CREATE TABLE user_table (
    user_idx  NUMBER
        CONSTRAINT user_pk PRIMARY KEY,
    user_name VARCHAR2(50) NOT NULL,
    user_id   VARCHAR2(100) NOT NULL,
    user_pw   VARCHAR2(100) NOT NULL
);

INSERT INTO user_table VALUES (
    user_seq.NEXTVAL,
    '테스트',
    'test',
    '1234'
);

CREATE TABLE content_table (
    content_idx        NUMBER
        CONSTRAINT content_pk PRIMARY KEY,
    content_subject VARCHAR2(500) NOT NULL,
    content_text LONG NOT NULL,
    content_file VARCHAR2(500),
    content_writer_idx NUMBER NOT NULL
        CONSTRAINT content_fk1
            REFERENCES user_table ( user_idx ),
    content_board_idx  NUMBER NOT NULL
        CONSTRAINT content_fk2
            REFERENCES board_info_table ( board_info_idx ),
    content_date       DATE NOT NULL
);

COMMIT;