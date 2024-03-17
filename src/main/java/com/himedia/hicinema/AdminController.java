package com.himedia.hicinema;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.himedia.hicinema.member.Member;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {


	@GetMapping(value = {"", "/"})
	public String admin(Model model) {
		model.addAttribute("title", "관리자 메인");
		return "admin/index";
	}

	@GetMapping("/index")
	public String index(Model model) {
		return "redirect:/admin";
	}

	@GetMapping("/error/505")
	public String error_505(Model model) {
		model.addAttribute("title", "error");
		return "admin/505";
	}

	

}
