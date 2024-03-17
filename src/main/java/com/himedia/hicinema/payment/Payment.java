package com.himedia.hicinema.payment;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Payment {
	@Id
	@Column(name = "pay_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String tid;

	private String payType;     // kakao

	private String type;        // store or movie

	private int price;

	private String status;
	private LocalDateTime regDate;
	private LocalDateTime delDate;

	private Long memberId;
}
