package com.hanbit.there.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.hanbit.there.api.service.MemberService;
import com.hanbit.there.api.vo.MemberVO;

@RestController
@RequestMapping("/api/member")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/signup")
	public Map signUp(@RequestParam("email") String email, @RequestParam("password") String password) {
		
		MemberVO memberVO = new MemberVO();
		memberVO.setEmail(email);
		memberVO.setPassword(password);
		
		memberService.signUp(memberVO);
		
		Map result = new HashMap();
		result.put("status", "ok");
		
		return result;
	}
	
	@PostMapping("/signin")
	public Map signIn(@RequestParam("email") String email, @RequestParam("password") String password, 
			@RequestParam("remember") boolean remember, HttpSession session, HttpServletResponse response) {		
		
		MemberVO memberVO = memberService.signIn(email, password);
		
		logger.debug(email + " has signed in.");
		
		if (remember) {
			Cookie cookie = new Cookie("Cookie", memberVO.getUid());
			cookie.setMaxAge(60); // 초 단위
			response.addCookie(cookie);
		}		
		
		// session 에 로그인 정보 (signedIn, uid, email)를 넣는다.
		session.setAttribute("signedIn", true);
		session.setAttribute("uid", memberVO.getUid());
		session.setAttribute("email", memberVO.getEmail());
		
		Map result = new HashMap();
		result.put("email", memberVO.getEmail());		
		
		return result;
	}
	
	@RequestMapping("/get")
	public Map getMember(HttpSession session) {
		Map member = new HashMap();
		
		if (session.getAttribute("signedIn") == null) {
			member.put("signedIn", false);
		}
		else {
			member.put("signedIn", true);
			member.put("email", session.getAttribute("email"));
		}
		
		return member;
	}
	
	@RequestMapping("/signout")
	public Map signOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		session.invalidate(); // 현재 session 폐기.
		
		Cookie cookie = WebUtils.getCookie(request, "Cookie");
		
		if (cookie != null) {
			cookie.setValue(null);
			response.addCookie(cookie);
		}
		
		Map result = new HashMap();		
		result.put("status", "ok");
		
		return result;
	}

}
