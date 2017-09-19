package com.hanbit.there.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.there.api.service.PartnersService;
import com.hanbit.there.api.vo.PartnersVO;

@RestController
@RequestMapping("/api/partners")
public class PartnersController {
	
	@Autowired
	private PartnersService partnersService;
	
	@RequestMapping("/{partnersId}")
	public PartnersVO getPartners(@PathVariable("partnersId") String partnersId) {
		
		return partnersService.getPartners(partnersId);
	}

}
