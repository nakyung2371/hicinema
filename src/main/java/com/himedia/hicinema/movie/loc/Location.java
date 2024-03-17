package com.himedia.hicinema.movie.loc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.himedia.hicinema.movie.theater.Theater;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="loc_id")
	private Long id;

	private String name;

	@Column(length = 1, nullable = false)
	private String status = "O";

//	@OneToMany(mappedBy = "location", cascade = CascadeType.ALL
//			, orphanRemoval = true, fetch = FetchType.LAZY)
//	private List<Theater> theaters;
}
