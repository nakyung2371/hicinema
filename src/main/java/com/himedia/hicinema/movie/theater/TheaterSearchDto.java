package com.himedia.hicinema.movie.theater;

import com.himedia.hicinema.movie.loc.Location;
import lombok.Data;

import java.util.Optional;

@Data
public class TheaterSearchDto {
	private String name;
	private String status;

	private Optional<Integer> page;

	private Location location;
}
