package com.himedia.hicinema.member;



import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.validation.constraints.AssertFalse.List;

public interface MemberRepository extends JpaRepository <Member,Long>{   //JpaRepository 인터페이스를 상속 
 
	//CRUD  Create, Read, Update, Delete 
	
   // ID로 member 엔티티를 조회
	Optional<Member> findByMemberId(String memberId );
	
	
   // 사용자 리스트 페이징 처리
   Page<Member> findAll(Pageable pageable);
	
}
