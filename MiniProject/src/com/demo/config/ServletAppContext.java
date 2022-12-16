package com.demo.config;

import javax.annotation.Resource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.demo.beans.LoginUserBean;
import com.demo.interceptor.CheckLoginInterceptor;
import com.demo.interceptor.CheckWriterInterceptor;
import com.demo.interceptor.MenuInterceptor;
import com.demo.mapper.BoardMapper;
import com.demo.mapper.MenuMapper;
import com.demo.mapper.UserMapper;
import com.demo.service.BoardService;
import com.demo.service.MenuService;

@Configuration // Spring MVC와 관련된 설정을 하는 클래스
@EnableWebMvc // Controller 어노테이션이 세팅되어 있는 클래스를 Controller로 등록
@ComponentScan("com.demo.controller") // 스캔할 패키지 지정
@ComponentScan("com.demo.service")
@ComponentScan("com.demo.beans")
@PropertySource("/WEB-INF/properties/db.properties")
public class ServletAppContext implements WebMvcConfigurer {

	/* Controller의 method가 반환하는 jsp의 이름 앞뒤에 경로와 확장자 설정 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		WebMvcConfigurer.super.configureViewResolvers(registry);

		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	/* 정적 파일의 경로를 mapping */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);

		registry.addResourceHandler("/**").addResourceLocations("/resources/");
	}

	/* 데이터베이스 접속 정보 관리 */
	@Value("${db.classname}")
	private String db_classname;

	@Value("${db.url}")
	private String db_url;

	@Value("${db.username}")
	private String db_username;

	@Value("${db.password}")
	private String db_password;

	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource source = new BasicDataSource();

		source.setDriverClassName(db_classname);
		source.setUrl(db_url);
		source.setUsername(db_username);
		source.setPassword(db_password);

		return source;
	}

	/* 쿼리문과 접속 관리하는 객체 */
	@Bean
	public SqlSessionFactory factory(BasicDataSource source) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(source);
		
		SqlSessionFactory factory = factoryBean.getObject();
		return factory;
	}

	/* 쿼리문 실행을 위한 mapper 객체 */
	@Bean
	public MapperFactoryBean<BoardMapper> getBoardMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<BoardMapper> factoryBean = new MapperFactoryBean<BoardMapper>(BoardMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		
		return factoryBean;
	}

	@Bean
	public MapperFactoryBean<MenuMapper> getMenuMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<MenuMapper> factoryBean = new MapperFactoryBean<MenuMapper>(MenuMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		
		return factoryBean;
	}

	@Bean
	public MapperFactoryBean<UserMapper> getUserMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<UserMapper> factoryBean = new MapperFactoryBean<UserMapper>(UserMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		
		return factoryBean;
	}

	/* 이미지 업로드 */
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

	/* 인터셉터 추가 */
	@Autowired
	private MenuService menuService;

	@Resource(name = "loginUserBean")
	private LoginUserBean loginUserBean;

	@Autowired
	private BoardService boardService;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);

		MenuInterceptor menuInterceptor = new MenuInterceptor(menuService, loginUserBean);
		InterceptorRegistration reg1 = registry.addInterceptor(menuInterceptor);
		reg1.addPathPatterns("/**"); // 모든 요청에 적용됨

		CheckLoginInterceptor checkLoginInterceptor = new CheckLoginInterceptor(loginUserBean);
		InterceptorRegistration reg2 = registry.addInterceptor(checkLoginInterceptor);
		reg2.addPathPatterns("/user/modify", "/user/logout", "/board/*");
		reg2.excludePathPatterns("/board/main");

		CheckWriterInterceptor checkWriterInterceptor = new CheckWriterInterceptor(loginUserBean, boardService);
		InterceptorRegistration reg3 = registry.addInterceptor(checkWriterInterceptor);
		reg3.addPathPatterns("/board/modify", "/board/delete");
	}

}
