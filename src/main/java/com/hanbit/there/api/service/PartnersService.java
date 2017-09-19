package com.hanbit.there.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.there.api.dao.PartnersDAO;
import com.hanbit.there.api.vo.PartnersVO;

@Service
public class PartnersService {
	
	@Autowired
	private PartnersDAO partnersDAO;
	
	public PartnersVO getPartners(String id) {
		PartnersVO partners = partnersDAO.selectPartners(id);
		
		return partners;
	}

}
