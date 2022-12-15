package com.demo.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.beans.ContentBean;
import com.demo.beans.LoginUserBean;
import com.demo.beans.PageBean;
import com.demo.mapper.BoardMapper;

@Service
@PropertySource("/WEB-INF/properties/option.properties")
public class BoardService {

	@Value("${path.upload}")
	private String path_upload;

	@Autowired
	private BoardMapper boardMapper;

	@Resource(name = "loginUserBean")
	private LoginUserBean loginUserBean;

	private String saveUploadFile(MultipartFile upload_file) {
		// 현재 시간을 이용해서 파일의 이름이 중복되지 않게 설정
		String file_name = System.currentTimeMillis() + "_" + upload_file.getOriginalFilename();
		// 파일과 관련된 오류(파일 찾지못함 등) 예외처리
		try {
			upload_file.transferTo(new File(path_upload + "/" + file_name));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file_name;
	}

	public void addContentInfo(ContentBean writeContentBean) {
//		System.out.println(writeContentBean.getContent_subject());
//		System.out.println(writeContentBean.getContent_text());
//		System.out.println(writeContentBean.getUpload_file().getSize());
		
		MultipartFile upload_file = writeContentBean.getUpload_file();
		if (upload_file.getSize() > 0) {
			// 위의 method로 파일을 저장하고 그 이름을 가져옴
			String file_name = saveUploadFile(upload_file);
			// 가져온 파일의 이름을 저장함
			writeContentBean.setContent_file(file_name);
		}
		// 글쓴이는 현재 로그인된 유저
		writeContentBean.setContent_writer_idx(loginUserBean.getUser_idx());
		boardMapper.addContentInfo(writeContentBean);
	}

	public String getBoardInfoName(int board_info_idx) {
		return boardMapper.getBoardInfoName(board_info_idx);
	}

	@Value("${page.listcnt}")
	private int page_listcnt;

	@Value("${page.paginationcnt}")
	private int page_paginationcnt;

	// 페이지에 해당하는 글들을 가져옴
	public List<ContentBean> getContentList(int board_info_idx, int page) {
		int start = (page - 1) * page_listcnt;
		// mybatis의 RowBounds 클래스를 사용(글시작 번호, 가져올 갯수)
		RowBounds rowBounds = new RowBounds(start, page_listcnt);
		return boardMapper.getContentList(board_info_idx, rowBounds);
	}

	public PageBean getContentCnt(int content_board_idx, int currentPage) {
		int content_cnt = boardMapper.getContentCnt(content_board_idx);
		PageBean pageBean = new PageBean(content_cnt, currentPage, page_listcnt, page_paginationcnt);
		return pageBean;
	}

	public ContentBean getContentInfo(int content_idx) {
		return boardMapper.getContentInfo(content_idx);
	}

	// 글 인덱스 번호로 검색해서 글 정보를 modifyContentBean에 입력
	public void getContents(ContentBean modifyContentBean) {
		ContentBean temp = boardMapper.getContentInfo(modifyContentBean.getContent_idx());
		modifyContentBean.setContent_writer_name(temp.getContent_writer_name());
		modifyContentBean.setContent_date(temp.getContent_date());
		modifyContentBean.setContent_subject(temp.getContent_subject());
		modifyContentBean.setContent_text(temp.getContent_text());
		modifyContentBean.setContent_file(temp.getContent_file());
	}

	public void modifyContentInfo(ContentBean modifyContentBean) {
		MultipartFile upload_file = modifyContentBean.getUpload_file();
		if (upload_file.getSize() > 0) {
			String file_name = saveUploadFile(upload_file);
			modifyContentBean.setContent_file(file_name);
		}
		boardMapper.modifyContentInfo(modifyContentBean);
	}

	public void deleteContentInfo(int content_idx) {
		boardMapper.deleteContentInfo(content_idx);
	}

}
