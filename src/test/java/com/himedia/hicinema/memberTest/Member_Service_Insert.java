package com.himedia.hicinema.memberTest;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.himedia.hicinema.member.Member;
import com.himedia.hicinema.member.MemberService;

@SpringBootTest
public class Member_Service_Insert {
	
	@Autowired
	private  MemberService memberService;

	@Test
	void Member_Service_Insert1() {
		
		memberService.create("홍길동", "aaa", "1234", "aaa@aaa.com", "01011112222"); 
		
		
		
	}
	

    @Test
    void memberInsert() {
        for (int i = 1; i <= 99; i++) {
            String name = String.format("나나나:[%03d]", i);
            String memberID = String.format("idididi:[%03d]", i);
            String password = String.format("0000:[%03d]", i);
            String email = String.format("abc@abc:[%03d]", i);
            String phone = String.format("010-123-123:[%03d]", i);
            
            this.memberService.create(name, memberID, password, email, phone);
        }
    }
   
	
//	this.memberRepository.save(m1);
    
    
}
