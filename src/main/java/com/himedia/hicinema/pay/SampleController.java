package com.himedia.hicinema.pay;

import com.google.gson.JsonObject;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/kakao")
public class SampleController {

	@Setter(onMethod_ = @Autowired)
	private KakaoPay kakaopay;

	@PostMapping("/kakaoPay")
	@ResponseBody
	public String kakaoPay(@RequestParam Map<String, Object> info) {
		log.info(".....................kakaoPay post.......................");
		System.out.println(info);
		String next_redirect_pc_url = kakaopay.kakaoPayReady(info);

//		return "redirect:" + kakaopay.kakaoPayReady();
		return next_redirect_pc_url;
	}

	@RequestMapping("/kakaoPaySuccess")
	public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model) {
		log.info("......................kakaoPaySuccess get......................");
		log.info("kakaoPaySuccess pg_token : " + pg_token);

		model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token));
		return "redirect:/movie/list";
	}

	@RequestMapping("/kakaoPaySuccessFail")
	public String kakaoPaySuccessFail() {
		return "kakaoPaySuccessFail";
	}

	@RequestMapping("/kakaoPayCancel")
	public String kakaoPayCancel() {
		return "kakaoPayCancel";
	}

}
