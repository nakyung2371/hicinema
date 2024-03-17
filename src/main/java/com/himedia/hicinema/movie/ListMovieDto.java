package com.himedia.hicinema.movie;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ListMovieDto {
	String movieCd;
	String title;
	String genre;
	String rats;
	String runtime;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime release;

	@QueryProjection
	public ListMovieDto(String movieCd, String title, String genre, String rats, String runtime, LocalDateTime release) {
		this.movieCd = movieCd;
		this.title = title;
		this.genre = genre;
		this.rats = rats;
		this.runtime = runtime;
		this.release = release;
	}
}
