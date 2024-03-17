package com.himedia.hicinema.movie.theater;

import com.himedia.hicinema.movie.loc.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Long>,
		QuerydslPredicateExecutor<Theater>, TheaterRepositoryCustom {

	List<Theater> findByStatusOrderByIdAsc(String status);

	List<Theater> findByLocationAndStatusOrderByIdAsc(Location location, String status);
}
