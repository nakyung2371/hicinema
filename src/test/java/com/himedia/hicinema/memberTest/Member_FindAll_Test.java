package com.himedia.hicinema.memberTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.himedia.hicinema.member.Member;
import com.himedia.hicinema.member.MemberRepository;

@SpringBootTest
public class Member_FindAll_Test {
	
	@Autowired
	MemberRepository memberRepository; 
	
	@Test
	void Member_FindAll_Test1() {
		
		Pageable pageable = PageRequest.of(0, 10);
		Page <Member> paging = 
				memberRepository.findAll(pageable); 
		
		System.out.println(paging.getNumber() );
		System.out.println(paging.nextPageable() );
		System.out.println(paging.getSize() );
		System.out.println(paging.getNumberOfElements() );
		System.out.println( );
		
		
		for (Member m : paging) {
			
			System.out.println(m.getEmail());
			System.out.println(m.getMemberId());
			System.out.println(m.getName());
			System.out.println(m.getPhone());
			System.out.println(m.getRole());
		
			 
		}
		
	}

}
