package com.demo.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringConfigClass extends AbstractAnnotationConfigDispatcherServletInitializer {

	/* DispatcherServlet에 mapping할 요청 주소를 세팅 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	/* Spring MVC 프로젝트 설정을 위한 클래스를 지정 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { ServletAppContext.class };
	}

	/* 프로젝트에서 사용할 Bean들을 정의하기 위한 클래스를 지정 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RootAppContext.class };
	}

	/* 파라미터 인코딩 필터 설정 */
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");

		return new Filter[] { encodingFilter };
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		super.customizeRegistration(registration);
		/*
		 * <1>파일 업로드 시 저장되는 경로(null이면 톰캣이 지정한 경로), <2>파일 1개당 최대 크기(5mb=5242880byte),
		 * <3>전체 파일 크기(50mb), <4>업로드 파일이 임시로 저장되지 않고 메모리에서 바로 스트림으로 전달되는 크기의 한계
		 */
		MultipartConfigElement config = new MultipartConfigElement(null, 5242880, 52428800, 0);
		registration.setMultipartConfig(config);
	}

}
