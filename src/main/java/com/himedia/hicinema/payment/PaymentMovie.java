package com.himedia.hicinema.payment;

import com.himedia.hicinema.member.Member;
import com.himedia.hicinema.movie.schedule.Schedule;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class PaymentMovie {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pay_movie_id")
	private Long id;

	private String title;
	private LocalDateTime screeningDate;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	private String scrDate;

	private String poster;

	//  영화관 정보
	private String theaterInfo;

	//  관람인원
	private String visitors;

	//  좌석
	private String seat;

	// 총 결제금액
	private int price;

	//  결제일시
	private LocalDateTime paymentDate;

	//  좌석 id ,로 구분
	private String seats_id;

	//  상태
	private String status;      // 기본(결제완료) : O, 삭제(취소) : X

	//  결제(종합)id
	private Long payId;

	private Long memberId;

	private Long scheduleId;
}
