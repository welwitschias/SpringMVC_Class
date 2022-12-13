package com.demo.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.demo.beans.BoardInfoBean;
import com.demo.service.MenuService;

public class MenuInterceptor implements HandlerInterceptor {

	// 생성자 주입
	private MenuService menuService;

	public MenuInterceptor(MenuService menuService) {
		this.menuService = menuService;
	}

	// 위 방법대신 Autowired 써도 됨(필드 주입)
//	@Autowired
//	private MenuService menuService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 테이블에서 게시판 이름들을 가져와 전달
		List<BoardInfoBean> topMenuList = menuService.getMenuList();
		request.setAttribute("topMenuList", topMenuList);

		return true;
	}

}
