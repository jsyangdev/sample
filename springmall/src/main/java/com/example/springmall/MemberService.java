package com.example.springmall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	@Autowired	// 연결해줌
	private MemberMapper memberMapper;
	public int getCountMember() {	
		
		return memberMapper.selectCountMember();

	}
}
