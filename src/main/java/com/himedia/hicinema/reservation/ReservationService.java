package com.himedia.hicinema.reservation;

import com.himedia.hicinema.member.Member;
import com.himedia.hicinema.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
	private final MemberRepository memberRepository;

	public Member getMember(String memberId) {
		return memberRepository.findByMemberId(memberId).get();
	}
}
