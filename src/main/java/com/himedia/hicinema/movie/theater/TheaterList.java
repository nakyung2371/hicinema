package com.himedia.hicinema.movie.theater;

import com.himedia.hicinema.movie.loc.Location;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class TheaterList {
	private Long id;
	private Location location;

	private String name;

	private String zipcode;
	private String addr;
	private String addr_detail;
	private String addr_ref;

	//상영관 수?
	private Long screenCount;
	private Integer seatCount;

	@QueryProjection

	public TheaterList(Long id, Location location, String name, String zipcode, String addr, String addr_detail, String addr_ref
			, Long screenCount
//			, Integer seatCount
	) {
		this.id = id;
		this.location = location;
		this.name = name;
		this.zipcode = zipcode;
		this.addr = addr;
		this.addr_detail = addr_detail;
		this.addr_ref = addr_ref;
		this.screenCount = screenCount;
//		this.seatCount = seatCount;
	}
}
