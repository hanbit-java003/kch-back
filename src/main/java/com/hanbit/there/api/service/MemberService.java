package com.hanbit.there.api.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.instantiator.sun.MagicInstantiator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hanbit.there.api.dao.MemberDAO;
import com.hanbit.there.api.exception.KchException;
import com.hanbit.there.api.vo.MemberVO;

@Service
public class MemberService {
	
	private static final char[] CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890".toCharArray();
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void signUp(MemberVO memberVO) {
		if (memberDAO.countMember(memberVO.getEmail()) > 0) {
			throw new KchException("이미 가입된 이메일 입니다.");
		}
		
		memberVO.setUid(generateUid());
		
		// 패스워드 암호화.
		// PasswordEncoder passwordEncoder = new StandardPasswordEncoder("hanbit"); // PasswordEncoder 를 Autowire 해서 주석 처리.
		String encodedPassword = passwordEncoder.encode(memberVO.getPassword()); // memberVO 에서 password 를 가져와서 PasswordEncoder 로 암호화.
		memberVO.setPassword(encodedPassword); // 암호화한 password 를 다시 memberVO 의 password 로 설정.
		
		memberDAO.insertMember(memberVO);
	}	
	
	public String generateUid() {
		int length = 12;
		char[] uid = new char[length];
		
		Random random = new Random();
		
		for (int i=0; i<length; i++) {
			uid[i] = CHARS[random.nextInt(CHARS.length)];
		}
		
		return new String(uid);
	}
	
	public MemberVO signIn(String email, String password) {
		MemberVO memberVO = memberDAO.selectMember(email);
		
		if (memberVO == null) {
			throw new KchException("가입되지 않은 이메일입니다.");
		}
		
		if (!passwordEncoder.matches(password, memberVO.getPassword())) {
			throw new KchException("잘못된 비밀번호입니다.");
		}
		
		return memberVO;
	}

}
