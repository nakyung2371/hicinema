package com.himedia.hicinema.upload;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<UploadFiles, Long> {
	UploadFiles findByImgName(String imgName);
}
