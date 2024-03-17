package com.himedia.hicinema.movie.schedule;

import com.himedia.hicinema.movie.Movie;
import com.himedia.hicinema.movie.Screen.Screen;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Data
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "schedule_id")
	private Long id;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime screeningDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime startTime;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime endTime;

	private String status = "O";

	private LocalDateTime regDate;
	private LocalDateTime delDate;


	@ManyToOne
	@JoinColumn(name = "movie_cd")
	private Movie movie;

	@ManyToOne
	@JoinColumn(name = "screen_id")
	private Screen screen;
}
