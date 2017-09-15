package com.hanbit.there.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.there.api.dao.MenuDAO;

@Service
public class MenuService {
	
	@Autowired
	private MenuDAO menuDAO;
	
	public String getMenu() {
		return null;
	}
	
}
