package com.example.springmall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional	// ★클래스 단위로 트랙젝션 처리
public class MemberService {
	@Autowired	// 연결해줌
	private MemberMapper memberMapper;
	public int getCountMember() {
		return memberMapper.selectCountMember();
		
	}
}