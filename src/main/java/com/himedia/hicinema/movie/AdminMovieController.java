package com.himedia.hicinema.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.himedia.hicinema.Crawling;
import com.himedia.hicinema.movie.Screen.Screen;
import com.himedia.hicinema.movie.Screen.ScreenService;
import com.himedia.hicinema.movie.loc.Location;
import com.himedia.hicinema.movie.loc.LocationService;
import com.himedia.hicinema.movie.schedule.Schedule;
import com.himedia.hicinema.movie.schedule.ScheduleService;
import com.himedia.hicinema.movie.seat.SeatService;
import com.himedia.hicinema.movie.theater.*;
import com.himedia.hicinema.upload.FileUploadService;
import com.himedia.hicinema.upload.UploadFiles;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/movie")
public class AdminMovieController {
	private final MovieService movieService;
	private final LocationService locationService;
	private final TheaterService theaterService;
	private final FileUploadService fileService;
	private final ScreenService screenService;
	private final ScheduleService scheduleService;
	private final SeatService seatService;

	@Autowired
	private HttpServletRequest request;

	@GetMapping("/theater_list")
	public String theaterList(Model model, TheaterSearchDto tsd, @RequestParam Optional<Integer> page) throws JsonProcessingException {
		model.addAttribute("title", "영화관 리스트");

		List<Location> loc_list = locationService.getList("O");
//		tsd.setStatus("O");
//		System.out.println(tsd);
//		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 3, 5);
//		Page<TheaterList> theaters = theaterService.getAdminTheaterList(tsd, pageable);
//
//		model.addAttribute("theaters", theaters);
//		model.addAttribute("tsd", tsd);
//		model.addAttribute("maxPage", 5);
//		model.addAttribute("httpServletRequest", request);
//
//		ObjectMapper om = new ObjectMapper();
//		om.registerModule(new JavaTimeModule());
//		String json = om.writeValueAsString(theaters);
//		System.out.println(json);

		model.addAttribute("locList", loc_list);
		return "admin/movie/theater_list";
	}

	@GetMapping("/theater/list")
	@ResponseBody
	public ResponseEntity<String> getTheaterList(TheaterSearchDto tsd) throws JsonProcessingException {
		JsonArray ja = new JsonArray();
		Pageable pageable = PageRequest.of(tsd.getPage().isPresent() ? tsd.getPage().get() : 0, 5);
		Page<TheaterList> theaters = theaterService.getAdminTheaterList(tsd, pageable);

		ObjectMapper om = new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		String json = om.writeValueAsString(theaters);
		log.info(json);
		ja.add(json);

		return new ResponseEntity<>(ja.toString(), HttpStatus.OK);
	}

	@GetMapping("/loc_list")
	public String locList(Model model) {
		model.addAttribute("title", "지역");
		return "admin/movie/loc_list";
	}
	@CrossOrigin(origins = "http://localhost:3333")
	@GetMapping("/loc_list/get")
	public ResponseEntity<String> locGetList() throws JsonProcessingException {
		JsonArray ja = new JsonArray();
		List<Location> list = locationService.getList("O");
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(list);

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	@PostMapping("/loc/post")
	@ResponseBody
	public ResponseEntity<String> locInsert(String name) {
		System.out.println(name);
		Location loc = new Location();
		loc.setName(name);

		JsonObject json = new JsonObject();
		try {
			locationService.create(loc);
			json.addProperty("msg", "success");
		} catch (Exception e) {
			e.printStackTrace();
			json.addProperty("msg", "error");
		}
		return new ResponseEntity<>(json.toString(), HttpStatus.OK);
	}

	@PostMapping("/loc/delete")
	@ResponseBody
	public ResponseEntity<String> locDelete(Location loc) {

		JsonObject json = new JsonObject();
		loc.setStatus("X");
		try {
			locationService.delete(loc);
			json.addProperty("msg", "success");
		} catch (Exception e) {
			e.printStackTrace();
			json.addProperty("msg", "error");
		}

		return new ResponseEntity<>(json.toString(), HttpStatus.OK);
	}

	@GetMapping("/theater_detail")
	public String theaterDetail(Model model, Long id) {
		model.addAttribute("title", "영화관 상세");
		Theater theater = new Theater();
		UploadFiles files = new UploadFiles();
		try {
			theater = theaterService.getDetail(id);
			files = fileService.getFiles(theater);
		}catch (Exception e) {
			e.printStackTrace();
		}
		List<Location> loc_list = locationService.getList("O");
		model.addAttribute("locList", loc_list);
		model.addAttribute("theater", theater);
		model.addAttribute("file", files);
		System.out.println(theater);
		return "admin/movie/theater_detail";
	}

	@GetMapping("/theater/screen/get")
	@ResponseBody
	public ResponseEntity<String> getScreen(Theater theater) throws JsonProcessingException {
//		System.out.println(theater);
		JsonObject jo = new JsonObject();
		try {
			Theater tht = theaterService.getDetail(theater.getId());
			List<Screen> screens = screenService.getScreenList(tht, "O");
			for(Screen sc : screens) {
				sc.setTheater(null);
			}
			ObjectMapper om = new ObjectMapper();
			om.registerModule(new JavaTimeModule());
			String json = om.writeValueAsString(screens);
			jo.addProperty("list", json);
			if(screens.size() > 0) {
				jo.addProperty("msg", "success");
			} else {
				jo.addProperty("msg", "null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.addProperty("msg", "error");
		}

		return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
	}

	@PostMapping("/theater/screen/post")
	@ResponseBody
	public ResponseEntity<String> postScreen(Screen screen, String theater_id) {
//		System.out.println(screen);
//		System.out.println(theater_id);
		JsonObject jo = new JsonObject();
		try {
			Theater theater = theaterService.getDetail(Long.valueOf(theater_id));
			screen.setTheater(theater);
			screenService.create(screen, 96);
			jo.addProperty("msg", "success");
		} catch (Exception e) {
			e.printStackTrace();
			jo.addProperty("msg", "error");
		}

		return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
	}

	@PostMapping("/theater/screen/delete")
	@ResponseBody
	public ResponseEntity<String> deleteScreen(Screen screen) {
		JsonObject jo = new JsonObject();
		try {
			screenService.deleteScreen(screen);
			jo.addProperty("msg", "success");
		} catch (Exception e) {
			e.printStackTrace();
			jo.addProperty("msg", "error");
		}

		return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
	}

	@GetMapping("/theater_form")
	public String theaterForm(Model model) {
		model.addAttribute("title", "영화관 등록");

		List<Location> loc_list = locationService.getList("O");
		model.addAttribute("locList", loc_list);
		return "admin/movie/theater_form";
	}

	@PostMapping("/theater/post")
	@ResponseBody
	public ResponseEntity<String> theaterNew(TheaterForm th, @RequestParam("img") List<MultipartFile> fileList) {
		JsonObject json = new JsonObject();
//		System.out.println(th);
//		System.out.println(fileList);
		try {
			theaterService.saveTheater(th, fileList);
			json.addProperty("msg", "success");
		} catch (Exception e) {
			e.printStackTrace();
			json.addProperty("msg", "fail");
		}
		return new ResponseEntity<>(json.toString(), HttpStatus.OK);
	}

	@GetMapping("/theater_screen")
	public String theaterScreen(Model model, Screen screen) {
		Screen sc = screenService.getScreen(screen.getId());

		model.addAttribute("screen", sc);
		model.addAttribute("title", "영화관 등록");
		return "admin/movie/theater_screen";
	}

	@GetMapping("/theater/screen/movie/list")
	@ResponseBody
	public ResponseEntity<String> getMovieList() {
		JsonObject jo = new JsonObject();
		try {
			List<Movie> movies = movieService.getMovieList("O");
			if(movies.size() > 0) {
				jo.addProperty("msg", "success");
				ObjectMapper om = new ObjectMapper();
				om.registerModule(new JavaTimeModule());
				String json = om.writeValueAsString(movies);
				jo.addProperty("movies", json);
			} else {
				jo.addProperty("msg", "null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.addProperty("msg", "error");
		}

		return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
	}

	@PostMapping("/theater/screen/schedule/post")
	@ResponseBody
	public ResponseEntity<String> postSchedule(Schedule schedule, String movieCd, String screenId) {
		JsonObject jo = new JsonObject();
		ZoneId zoneId = ZoneId.of("Asia/Seoul"); // 또는 원하는 시간대로 설정
		ZonedDateTime zonedDateTime = schedule.getScreeningDate().atZone(ZoneOffset.UTC).withZoneSameInstant(zoneId);
		LocalDateTime scd = zonedDateTime.toLocalDateTime();
		ZonedDateTime zonedDateTime1 = schedule.getStartTime().atZone(ZoneOffset.UTC).withZoneSameInstant(zoneId);
		LocalDateTime sd = zonedDateTime1.toLocalDateTime();
		ZonedDateTime zonedDateTime2 = schedule.getEndTime().atZone(ZoneOffset.UTC).withZoneSameInstant(zoneId);
		LocalDateTime ed = zonedDateTime2.toLocalDateTime();
		schedule.setScreeningDate(scd);
		schedule.setStartTime(sd);
		schedule.setEndTime(ed);


		System.out.println(schedule);
		System.out.println(movieCd);
		System.out.println(screenId);

		try {
			scheduleService.inert(schedule, movieCd, Long.valueOf(screenId));
			jo.addProperty("msg", "success");
		} catch (Exception e) {
			e.printStackTrace();
			jo.addProperty("msg", "error");
		}

		return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
	}

	@GetMapping("/theater/screen/schedule/list/get")
	@ResponseBody
	public ResponseEntity<String> getScheduleList(Schedule schedule, String screenId) {
		JsonObject jo = new JsonObject();
		ZoneId zoneId = ZoneId.of("Asia/Seoul");
		ZonedDateTime zonedDateTime = schedule.getScreeningDate().atZone(ZoneOffset.UTC).withZoneSameInstant(zoneId);
		LocalDateTime scd = zonedDateTime.toLocalDateTime();

		try {
			List<Schedule> list = scheduleService.getSchduleList(Long.valueOf(screenId), scd, "O");
			if(list.size() > 0) {
				for(Schedule sch : list) {
//					sch.setMovie(null);
					sch.setScreen(null);
				}
				ObjectMapper om = new ObjectMapper();
				om.registerModule(new JavaTimeModule());
				String json = om.writeValueAsString(list);
				jo.addProperty("msg", "success");
				jo.addProperty("schedules", json);
			} else {
				jo.addProperty("msg", "null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.addProperty("msg", "error");
		}


		return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
	}

	@GetMapping("/movie_list")
	public String movieList(Model model) {
		model.addAttribute("title", "상영중");
		return "admin/movie/movie_list";
	}

	@CrossOrigin(origins = "http://localhost:3333")
	@GetMapping("/list/get")
	@ResponseBody
	public ResponseEntity<String> getList(MovieSearchDto mv) throws JsonProcessingException {
		JsonArray ja = new JsonArray();
		System.out.println("get_list");
		System.out.println(mv);
		Pageable pageable = PageRequest.of(mv.getPage().isPresent() ? mv.getPage().get() : 0, 5);
		Page<ListMovieDto> movies = movieService.getAdminMoviePage(mv, pageable);

		ObjectMapper om = new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		String json = om.writeValueAsString(movies);
		log.info(json);
		ja.add(json);

		return new ResponseEntity<>(ja.toString(), HttpStatus.OK);
	}

	@GetMapping("/movie_listSoon")
	public String movieList2(Model model) {
		model.addAttribute("title", "상영예정");
		return "admin/movie/movie_list2";
	}

	@GetMapping("/movie_listEnd")
	public String movieList3(Model model) {
		model.addAttribute("title", "상영종료");
		return "admin/movie/movie_list3";
	}

	@GetMapping("/movie_form")
	public String movieForm(Model model) {
		model.addAttribute("title", "영화 등록");
		return "admin/movie/movie_form";
	}

	@GetMapping("/movie_detail")
	public String movieDetail(Model model, String movieCd) {
		model.addAttribute("title", "영화 상세");
		try {
			Movie mv = movieService.getAdminMovieDetail(movieCd);
			System.out.println(mv);
			model.addAttribute("movie", mv);
			return "admin/movie/movie_detail";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/movie/movie_list";
		}

	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/movie_crawling")
	public String movieCrawling(Model model) {
		model.addAttribute("title", "영화 리스트 업데이트");
		return "admin/movie/movie_crawling";
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/crawling/get_info")
	@ResponseBody
	public String get_info() throws InterruptedException {
		log.info("============crawling start==========");
		String data = Crawling.crawling_movie_list();
		log.info("============crawling end============");
//		System.out.println(data);
		return data;
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/crawling/get_details")
	@ResponseBody
	public ResponseEntity<String> updateMovie(@RequestBody List<Movie> mvs) throws InterruptedException {
		JsonArray ja = new JsonArray();
		Gson gson = new Gson();
		String data = "";
		for(Movie mv : mvs) {
			System.out.println(mv.getMovieCd());
			Movie movie = Crawling.get_movie(mv);
			movieService.create(movie);
			ja.add(mv.getMovieCd());
		}
//		JsonObject jo = new JsonObject();
//		jo.addProperty("msg", "success");
//		jo.addProperty("list", String.valueOf(ja));
		return new ResponseEntity<>(ja.toString(), HttpStatus.OK);
	}
	
}
