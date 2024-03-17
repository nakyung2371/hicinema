package com.himedia.hicinema.movie.Screen;

import com.himedia.hicinema.movie.theater.Theater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScreenService {
	private final ScreenRepository screenRepository;

	public List<Screen> getScreenList(Theater theater, String status) {
		return screenRepository.findByTheaterAndStatusOrderByIdAsc(theater, status);
	}

	public void create(Screen screen, int seat) {
		screen.setRegDate(LocalDateTime.now());
		screen.setSeat(seat);
		System.out.println("seat");
		System.out.println(seat);
		screenRepository.save(screen);
	}

	public Screen getScreen(Long screenId) {
		return screenRepository.findById(screenId).get();
	}

	public void deleteScreen(Screen screen) {
		Screen sc = screenRepository.findById(screen.getId()).get();
		sc.setStatus("X");
		sc.setDelDate(LocalDateTime.now());
		screenRepository.save(sc);
	}
}
