package com.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.demo.beans.ContentBean;

public interface BoardMapper {

	/* content_idx 값이 나오면 먼저(before) 쿼리문을 실행, 결과를 입력한다. */
	@SelectKey(statement = "SELECT content_seq.nextval FROM dual", keyProperty = "content_idx", before = true, resultType = int.class)
	/* jdbcType=VARCHAR : 마이바티스에서 null값 가능하게 해줌 */
	@Insert("INSERT INTO content_table(content_idx, content_subject, content_text, "
			+ "content_file, content_writer_idx, content_board_idx, content_date) "
			+ "VALUES (#{content_idx}, #{content_subject}, #{content_text}, #{content_file, jdbcType=VARCHAR}, "
			+ "#{content_writer_idx}, #{content_board_idx}, sysdate)")
	void addContentInfo(ContentBean writeContentBean);

	@Select("SELECT board_info_name FROM board_info_table WHERE board_info_idx = #{board_info_idx}")
	String getBoardInfoName(int board_info_idx);

	/* TO_CHAR 사용 시 type을 String으로 지정해야 함(Date 안됨) */
	@Select("SELECT t1.content_idx, t1.content_subject, t2.user_name AS content_writer_name, "
			+ "TO_CHAR(t1.content_date, 'YYYY-MM-DD') AS content_date " + "FROM content_table t1 JOIN user_table t2 "
			+ "ON t1.content_writer_idx = t2.user_idx "
			+ "AND t1.content_board_idx = #{board_info_idx} ORDER BY t1.content_idx DESC")
	List<ContentBean> getContentList(int board_info_idx, RowBounds rowBounds);

	@Select("SELECT count(*) FROM content_table WHERE content_board_idx = ${content_board_idx}")
	int getContentCnt(int content_board_idx);

	@Select("SELECT t2.user_name AS content_writer_name, " + "TO_CHAR(t1.content_date, 'YYYY-MM-DD') AS content_date,"
			+ "t1.content_subject, t1.content_text, t1.content_file, t1.content_writer_idx "
			+ "FROM content_table t1 JOIN user_table t2 " + "ON t1.content_writer_idx = t2.user_idx "
			+ "AND content_idx = #{content_idx}")
	ContentBean getContentInfo(int content_idx);

	@Update("UPDATE content_table " + "SET content_subject = #{content_subject}, content_text = #{content_text}, "
			+ "content_file = #{content_file, jdbcType=VARCHAR} " + "WHERE content_idx = #{content_idx}")
	void modifyContentInfo(ContentBean modifyContentBean);

	@Delete("DELETE FROM content_table WHERE content_idx = #{content_idx}")
	void deleteContentInfo(int content_idx);

}
