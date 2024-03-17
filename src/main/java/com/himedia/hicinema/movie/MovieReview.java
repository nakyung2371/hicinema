package com.himedia.hicinema.movie;

import com.himedia.hicinema.member.Member;
import lombok.Data;

@Data
public class MovieReview {

	private long id;

	private double rating;
	private String content;
	private Member member;
	private Movie movie;
}
