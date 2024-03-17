package com.himedia.hicinema.movie.seat;

import com.himedia.hicinema.movie.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
	List<Seat> findByScheduleOrderByIdAsc(Schedule schedule);
}
