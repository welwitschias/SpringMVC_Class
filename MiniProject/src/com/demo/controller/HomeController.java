package com.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		/* 이클립스에서 톰캣의 실제 구동주소 확인하기 */
//		System.out.println(request.getServletContext().getRealPath("/"));
		return "redirect:/main";
	}

}
