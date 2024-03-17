package com.himedia.hicinema.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.himedia.hicinema.movie.AdminMovieController;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/notice")

public class AdminNoticeController {
	@GetMapping("/notice_form")
	public String eventList(Model model) {
		model.addAttribute("title", "공지사항 등록");
		return "admin/notice/notice_form";
	}

	private final NoticeRepository noticeRepository;
	@GetMapping("/notice_list")
	public String list(Model model) {
		List<Notice> noticeList = this.noticeRepository.findAll();
		model.addAttribute("title", "공지사항 목록");
		model.addAttribute("noticeList", noticeList);
		return "admin/notice/notice_list";
	}

	@GetMapping("/notice_detail")
	public String eventDetail(Model model) {
		model.addAttribute("title", "공지사항 수정");
		return "admin/notice/notice_detail";
	}
}