package com.himedia.hicinema.movie.schedule;

import com.himedia.hicinema.movie.Movie;
import com.himedia.hicinema.movie.MovieService;
import com.himedia.hicinema.movie.Screen.Screen;
import com.himedia.hicinema.movie.Screen.ScreenService;
import com.himedia.hicinema.movie.seat.Seat;
import com.himedia.hicinema.movie.seat.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ScheduleService {
	private final ScheduleRepository scheduleRepository;
	private final MovieService movieService;
	private final ScreenService screenService;
	private final SeatRepository seatRepository;

	@Transactional
	public void inert(Schedule schedule, String movieCd, Long screenId) {
		Movie movie = movieService.getMovie(movieCd, "O");
		Screen screen = screenService.getScreen(screenId);
		schedule.setMovie(movie);
		schedule.setScreen(screen);
		schedule.setRegDate(LocalDateTime.now());
		Schedule sch =  scheduleRepository.save(schedule);
		System.out.println("=======================================");
		System.out.println(sch.getId());
		System.out.println("=======================================");

		int cnt = screen.getSeat();
		List<String> al_list = new ArrayList<>();
		for (char alp = 'A'; alp <= 'Z'; alp++) {
			al_list.add(String.valueOf(alp));
		}
		for (int i = 0; i < cnt; i++) {
			Seat seat = new Seat();
			int alphabetIndex = i / 24;
			int numericIndex = i % 24;
			seat.setName(al_list.get(alphabetIndex) + String.format("%02d", numericIndex + 1));
			seat.setScreen(screen);
			seat.setSchedule(sch);
			seat.setStatus("O");
			seat.setRegDate(LocalDateTime.now());
			seatRepository.save(seat);
		}
	}

	public List<Schedule> getSchduleList(Long screenId, LocalDateTime screeningDate, String status) {
		return scheduleRepository.findByScreenIdAndScreeningDateAndStatusOrderByIdAsc(screenId, screeningDate, status);
	}

	public List<Map<String, Object>> getUserPageSchedule(String movieCd, LocalDateTime screeningDate, Long theaterId) {
		return scheduleRepository.findUserPageSchedule(movieCd, screeningDate, theaterId);
	}

	public Schedule getSchedule(Long id) {
		return scheduleRepository.findById(id).get();
	}
}
