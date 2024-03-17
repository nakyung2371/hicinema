package com.himedia.hicinema.movie.Screen;

import com.himedia.hicinema.movie.theater.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ScreenRepository extends JpaRepository<Screen, Long> {

	List<Screen> findByTheaterAndStatusOrderByIdAsc(Theater theaterm, String status);
}
