package com.himedia.hicinema.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MovieService {

	private final MovieRepository mvRepository;

	public void create(Movie mv) {
		mvRepository.save(mv);
	}

	@Transactional(readOnly = true)
	public Page<ListMovieDto> getAdminMoviePage(MovieSearchDto mv, Pageable pageable) {
		return mvRepository.getAdminMoviePage(mv, pageable);
	}

	public Movie getAdminMovieDetail(String movieCd) {
		return mvRepository.findByMovieCd(movieCd).get();
	}

	public List<Movie> getUserMoviePage(MovieSearchDto mv) {
		return mvRepository.getUserMoviePage(mv);
	}

	public List<Movie> getMovieList(String status) {
		return mvRepository.findByStatusOrderByRegDateAsc(status);
	}

	public Movie getMovie(String movieCd, String status) {
		return mvRepository.findByMovieCdAndStatus(movieCd, status).get();
	}
}
