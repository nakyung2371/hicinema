package com.himedia.hicinema.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieRepositoryCustom {


	Page<ListMovieDto> getAdminMoviePage(MovieSearchDto mv, Pageable pageable);
	List<Movie> getUserMoviePage(MovieSearchDto mv);

}
