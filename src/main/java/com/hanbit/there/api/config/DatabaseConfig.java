package com.hanbit.there.api.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration // 설정 파일임을 알려준다.
@EnableAutoConfiguration
@EnableTransactionManagement
public class DatabaseConfig { // mybatis를 통해 database에 접속할 수 있는 환경설정
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean // bean 등록
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception { // bean타입 bean이름
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml")); // 설정 파일의 위치
		sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:mybatis/mappers/**/*.xml"));
		
		return sqlSessionFactory.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) { // Bean 중에서 찾아서 넣어준다
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}
