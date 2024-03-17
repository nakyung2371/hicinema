package com.himedia.hicinema.upload;

import com.himedia.hicinema.movie.theater.Theater;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class FileUploadService {
	@Value("${fileLocation}")
	private String fileLocation;

	private final FileRepository fileRepository;

	private final FileService fileService;



	public String saveItemImg(UploadFiles fi, MultipartFile multipartFile) throws Exception{

		// oriImgNanme : MultipartFile에서 넘어오는 원본이미지 이름을
		String oriImgName = multipartFile.getOriginalFilename();		// 원본 이미지 파일 이름
		String imgName = "";										// 서버에 저장할 이미지 이름
		String imgUrl = "";											// 전체 이미지

		//파일 업로드 ( 파일을 서버 시스템의 물리적인 경로에 저장후 UUID.jpg 리턴
		// 파일을 실제 시스템에 저장
		if(!StringUtils.isEmpty(oriImgName)){
			imgName = fileService.uploadFile(fileLocation, oriImgName,
					multipartFile.getBytes());
			imgUrl = "/upload/" + imgName;
		}


//		//상품 이미지 정보 저장 : DB에 UploadFiles 테이블에 이미지 정보를 저장.
//		fi.updateItemImg(oriImgName, imgName, imgUrl);
		fi.setOriImgName(oriImgName);
		fi.setImgName(imgName);
		fi.setImgUrl(imgUrl);
		fi.setRegDate(LocalDateTime.now());
		fileRepository.save(fi);
		return imgName;
	}

//	public void updateItemImg(Long fileId, MultipartFile multipartFile) throws Exception{
//
//
//		if(!multipartFile.isEmpty()){
//			UploadFiles savedItemImg = fileRepository.findById(fileId)
//					.orElseThrow(EntityNotFoundException::new);
//
//			//기존 이미지 파일 삭제
//			if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
//				fileService.deleteFile(fileLocation+"/"+
//						savedItemImg.getImgName());
//			}
//
//			String oriImgName = multipartFile.getOriginalFilename();
//			String imgName = fileService.uploadFile(fileLocation, oriImgName, multipartFile.getBytes());
//			String imgUrl = "/images/item/" + imgName;
////			savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
//		}
//	}

	public UploadFiles getFiles(Theater theater) {
		return fileRepository.findById(Long.valueOf(theater.getFile_id())).get();
	}

	public UploadFiles getFile(String file_id) {
		return fileRepository.findById(Long.valueOf(file_id)).get();
	}
}
