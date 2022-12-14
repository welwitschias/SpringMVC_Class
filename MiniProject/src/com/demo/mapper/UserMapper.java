package com.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.demo.beans.LoginUserBean;
import com.demo.beans.UserBean;

public interface UserMapper {

	@Select("SELECT user_name FROM user_table WHERE user_id = #{user_id}")
	String checkUserIdExist(String user_id);

	@Insert("INSERT INTO user_table (user_idx, user_name, user_id, user_pw) "
			+ "VALUES (user_seq.nextval, #{user_name}, #{user_id}, #{user_pw})")
	void addUserInfo(UserBean joinUserBean);

	@Select("SELECT user_idx, user_name FROM user_table WHERE user_id = #{user_id} AND user_pw = #{user_pw}")
	LoginUserBean getLoginUserInfo(LoginUserBean loginBean);

	@Select("SELECT user_id, user_name FROM user_table WHERE user_idx = #{user_idx}")
	UserBean getModifyUserInfo(int user_idx);

	@Update("UPDATE user_table SET user_pw = #{user_pw} WHERE user_idx = #{user_idx}")
	void modifyUserInfo(UserBean modifyUserBean);

}
