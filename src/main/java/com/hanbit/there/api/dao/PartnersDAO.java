package com.hanbit.there.api.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanbit.there.api.vo.PartnersVO;

@Repository
public class PartnersDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public PartnersVO selectPartners(String id) {
		return sqlSession.selectOne("partners.selectPartners", id);
	}

}
