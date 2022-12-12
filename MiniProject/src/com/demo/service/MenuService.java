package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.beans.BoardInfoBean;
import com.demo.mapper.MenuMapper;

@Service
public class MenuService {

	@Autowired
	private MenuMapper menuMapper;

	public List<BoardInfoBean> getMenuList() {
		List<BoardInfoBean> menuList = menuMapper.getMenuList();
		return menuList;
	}

}
