package com.himedia.hicinema.movie.seat;

import com.himedia.hicinema.movie.Screen.Screen;
import com.himedia.hicinema.movie.schedule.Schedule;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Seat {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "seat_id")
	private Long id;

	private String name;

	private String status;      // 기본 : O , 선택불가 : X
	private LocalDateTime regDate;
	private LocalDateTime delDate;

	@ManyToOne
	@JoinColumn(name = "screen_id")
	private Screen screen;

	@ManyToOne
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;
}
