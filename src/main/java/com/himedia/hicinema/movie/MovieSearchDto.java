package com.himedia.hicinema.movie;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class MovieSearchDto {

	private String title;
	private String genre;
	private Optional<Integer> page;
	private String status;
	private LocalDateTime screenOutDate;
	private String type;
}
