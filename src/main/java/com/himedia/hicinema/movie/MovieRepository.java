package com.himedia.hicinema.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, String>,
		QuerydslPredicateExecutor<Movie>, MovieRepositoryCustom {
	Optional<Movie> findByTitle(String title);
	Optional<Movie> findByMovieCd(String movieCd);

	List<Movie> findByStatusOrderByRegDateAsc(String status);

	Optional<Movie> findByMovieCdAndStatus(String movieCd, String status);


}
