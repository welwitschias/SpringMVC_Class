package com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.demo.beans.ContentBean;
import com.demo.service.MainService;

@Controller
public class MainController {

	@Autowired
	private MainService mainService;

	@GetMapping("/main")
	public String main(Model model) {
		ArrayList<List<ContentBean>> list = new ArrayList<List<ContentBean>>();

		list.add(mainService.getMainList(1)); // 1번 게시판 게시글 5개 리스트 추가
		list.add(mainService.getMainList(2)); // 2번 게시판 게시글 5개 리스트 추가
		list.add(mainService.getMainList(3)); // 3번 게시판 게시글 5개 리스트 추가
		list.add(mainService.getMainList(4)); // 4번 게시판 게시글 5개 리스트 추가
		model.addAttribute("list", list);

		return "main";
	}

}
