package com.himedia.hicinema.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMovieRepository extends JpaRepository<PaymentMovie, Long> {
	List<PaymentMovie> findByMemberIdAndStatusOrderByIdDesc(Long memberId, String status);
}
