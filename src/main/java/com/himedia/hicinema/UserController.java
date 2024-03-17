package com.himedia.hicinema;

import com.himedia.hicinema.member.Member;
import com.himedia.hicinema.payment.PaymentMovie;
import com.himedia.hicinema.payment.PaymentService;
import com.himedia.hicinema.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
	private final ReservationService reservationService;
	private final PaymentService paymentService;

	@GetMapping(value={"", "/"})
	public String index(Model model) {
		model.addAttribute("title", "메인");
		return "user/main";
	}

	@GetMapping("/index")
	public String index2(Model model) {
		model.addAttribute("title", "관리자 메인");
		return "redirect:/";
	}

	@GetMapping("/member/mypage/ticketing")
	public String ticketing(Model model, Principal principal) {
		List<PaymentMovie> list = new ArrayList<>();
		try {
			Member member = reservationService.getMember(principal.getName());
			list = paymentService.getPaymentList(member.getId(), "O");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("title", "예매내역");
		model.addAttribute("payList", list);
		return "member/mypage/mypage_ticketing_knk";
	}

}
