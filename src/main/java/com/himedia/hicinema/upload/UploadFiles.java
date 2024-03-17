package com.himedia.hicinema.upload;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class UploadFiles {
	@Id
	@Column(name="files_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String imgName; // 파일명

	private String oriImgName; //원본 파일명

	private String imgUrl; // 조회 경로

	private String repimgYn; //대표 파일 여부

	private LocalDateTime regDate;

	private LocalDateTime delDate;

}
