package com.himedia.hicinema.movie.seat;

import com.himedia.hicinema.movie.Screen.Screen;
import com.himedia.hicinema.movie.schedule.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SeatService {
	private final SeatRepository seatRepository;

	public void insertSeat(Screen screen, Schedule schedule) {
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
			seat.setSchedule(schedule);
			seat.setStatus("O");
			seatRepository.save(seat);
		}
	}

	public List<Seat> getSeatList(Schedule schedule) {
		return seatRepository.findByScheduleOrderByIdAsc(schedule);
	}
}
