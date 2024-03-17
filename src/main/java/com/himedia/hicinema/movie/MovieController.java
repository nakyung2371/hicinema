package com.himedia.hicinema.movie;

import com.himedia.hicinema.member.Member;
import com.himedia.hicinema.member.MemberSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

	private final MovieService movieService;

	@GetMapping("/list")
	public String getList(Model model, @RequestParam String type, Principal principal) {
		model.addAttribute("title", "Movie");
		model.addAttribute("type", type);

		List<String> img_arr = new ArrayList<>();
		img_arr.add("/img_nsy/aespa_19207742.jpg");
		img_arr.add("/img_nsy/BabyShark.jpg");
		img_arr.add("/img_nsy/IfOnly_1920774.jpg");

		model.addAttribute("images", img_arr);
		MovieSearchDto mv1 = new MovieSearchDto();
		mv1.setStatus("O");
		mv1.setType("now");
		List<Movie> list1 = movieService.getUserMoviePage(mv1);
		MovieSearchDto mv2 = new MovieSearchDto();
		mv2.setStatus("O");
		mv2.setType("soon");
		List<Movie> list2 = movieService.getUserMoviePage(mv2);
		MovieSearchDto mv3 = new MovieSearchDto();
		mv3.setStatus("O");
		mv3.setType("end");
		List<Movie> list3 = movieService.getUserMoviePage(mv3);

		model.addAttribute("now", list1);
		model.addAttribute("soon", list2);
		model.addAttribute("end", list3);
		return "user/movie_list";
	}

	@GetMapping("/detail/{movieCd}")
	public String getMovie(Model model, @PathVariable String movieCd) {
		model.addAttribute("title", "Movie");

		return "user/movie_detail";
	}
}
