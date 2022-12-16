DECLARE
    cnt NUMBER := 0;
BEGIN
    LOOP
        EXIT WHEN cnt > 999;
        cnt := cnt + 1;
        INSERT INTO content_table (
            content_idx,
            content_subject,
            content_text,
            content_file,
            content_writer_idx,
            content_board_idx,
            content_date
        ) VALUES (
            content_seq.NEXTVAL,
            '테스트 제목 ' || to_char(cnt),
            '테스트 내용 ' || to_char(cnt),
            NULL,
            1,
            1,
            sysdate
        );

    END LOOP;
END;

COMMIT;