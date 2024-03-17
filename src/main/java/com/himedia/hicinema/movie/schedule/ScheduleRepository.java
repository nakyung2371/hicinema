package com.himedia.hicinema.movie.schedule;

import com.himedia.hicinema.movie.theater.Theater;
import com.himedia.hicinema.movie.theater.TheaterRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {
	List<Schedule> findByScreenIdAndScreeningDateAndStatusOrderByIdAsc(Long screenId, LocalDateTime screeningDate, String status);


}
