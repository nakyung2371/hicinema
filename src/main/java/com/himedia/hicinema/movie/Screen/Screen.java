package com.himedia.hicinema.movie.Screen;

import com.himedia.hicinema.movie.loc.Location;
import com.himedia.hicinema.movie.schedule.Schedule;
import com.himedia.hicinema.movie.theater.Theater;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Screen {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "screen_id")
	private Long id;

	private String name;
	private String status = "O";
	private int seat;
	private LocalDateTime regDate;
	private LocalDateTime delDate;
	private String seatType = "A";


	@ManyToOne
	@JoinColumn(name = "theater_id")
	private Theater theater;

}
