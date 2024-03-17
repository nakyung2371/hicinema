package com.himedia.hicinema.payment;

import com.himedia.hicinema.movie.seat.Seat;
import com.himedia.hicinema.movie.seat.SeatRepository;
import com.himedia.hicinema.pay.KakaoPayApprovalVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService {
	private final PaymentRepository paymentRepository;
	private final PaymentMovieRepository paymentMovieRepository;
	private final SeatRepository seatRepository;

	public Long savePayment(KakaoPayApprovalVO kpa, Long mb_id) {
		Payment payment = new Payment();
		payment.setTid(kpa.getTid());
		payment.setPayType("kakaopay");
		payment.setType("movie");
		payment.setStatus("O");
		payment.setRegDate(LocalDateTime.now());
		payment.setMemberId(mb_id);
		Payment pay = paymentRepository.save(payment);
		return pay.getId();
	}

	public void savePaymentMovie(PaymentMovie pm, Long mb_id) {
		pm.setMemberId(mb_id);
		pm.setPaymentDate(LocalDateTime.now());
		pm.setStatus("O");
		paymentMovieRepository.save(pm);
		for(String seat_id : pm.getSeats_id().split(",")) {
			Seat seat = seatRepository.findById(Long.valueOf(seat_id)).get();
			seat.setStatus("P");
			seatRepository.save(seat);
		}
	}

	public List<PaymentMovie> getPaymentList(Long memberId, String status) {
		return paymentMovieRepository.findByMemberIdAndStatusOrderByIdDesc(memberId, status);
	}

}
