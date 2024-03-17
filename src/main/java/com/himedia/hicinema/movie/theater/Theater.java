package com.himedia.hicinema.movie.theater;

import com.himedia.hicinema.movie.loc.Location;
import com.himedia.hicinema.upload.UploadFiles;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
public class Theater {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="theater_id")
	private Long id;

	private String name;

	private String zipcode;
	private String addr;
	private String addr_detail;
	private String addr_ref;

	private String file_id;
	private String content;

	@Column(nullable = false)
	private String status = "O";
	private LocalDateTime regDate;
	private LocalDateTime delDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="loc_id")
	private Location location;
}
