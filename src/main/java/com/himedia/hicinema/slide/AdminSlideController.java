package com.himedia.hicinema.slide;

import java.util.List;

import com.google.gson.JsonObject;
import com.himedia.hicinema.movie.loc.Location;
import com.himedia.hicinema.movie.theater.TheaterForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.himedia.hicinema.movie.AdminMovieController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/slide")
public class AdminSlideController {

	private final MainSlideService mainSlideService;


	//mainslide_list
	@GetMapping("/mainslide_list")
	public String mainSlideList(Model model) {
		model.addAttribute("title", "메인슬라이드 목록");
		return "admin/slide/mainslide_list";
	}

	@GetMapping("/mainslide_form")
	public String mainslideForm(Model model) {
		model.addAttribute("title", "메인슬라이드 등록");
		return "admin/slide/mainslide_form";
	}

	@GetMapping("/mainslide_detail")
	public String mainslideDetail(Model model) {
		model.addAttribute("title", "메인슬라이드 수정");
		return "admin/slide/mainslide_detail";
	}

//	@PostMapping("/mainslide/post")걍죽을ㄱ까...?
//	@ResponseBody
//	public ResponseEntity<String> mainslideNew(TheaterForm th, @RequestParam("img") List<MultipartFile> fileList) {
//		JsonObject json = new JsonObject();
//		try {
//			mainSlideService.saveTheater(th, fileList);
//			json.addProperty("msg", "success");
//		} catch (Exception e) {
//			e.printStackTrace();
//			json.addProperty("msg", "fail");
//		}
//
//
//		return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//	}
}
