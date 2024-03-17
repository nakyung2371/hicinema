package com.himedia.hicinema.movie.theater;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TheaterRepositoryCustom {
	Page<TheaterList> getAdminTheaterList(TheaterSearchDto tsd, Pageable pageable);

}
