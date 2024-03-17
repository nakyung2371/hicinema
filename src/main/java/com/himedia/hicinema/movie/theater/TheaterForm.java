package com.himedia.hicinema.movie.theater;

import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class TheaterForm {
	private Long id;
	private String name;

	private String zipcode;
	private String addr;
	private String addr_detail;
	private String addr_ref;

	private long file_id;   //여러개면 ,로 구분
	private String content;
	private long loc_id;

	private static ModelMapper modelMapper = new ModelMapper();

	public Theater createTheater() {
		return modelMapper.map(this, Theater.class);
	}
}
