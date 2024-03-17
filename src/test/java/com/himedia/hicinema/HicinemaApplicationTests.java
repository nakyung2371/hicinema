package com.himedia.hicinema;

import com.himedia.hicinema.notice.Notice;
import com.himedia.hicinema.notice.NoticeRepository;
import com.himedia.hicinema.slide.MainSlide;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.himedia.hicinema.slide.MainSlideRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

@SpringBootTest
class HicinemaApplicationTests {

//	@Test
//	void contextLoads() {
//	}

//	@Autowired
//	private NoticeRepository noticeRepository;

//	@Test
//	void testJpa() {
//		Notice n1 = new Notice();
//		n1.setTitle("공지사항1");
//		n1.setCategory("전체");
//		n1.setContent("전체 공지사항1");
//		n1.setCreateDate(LocalDateTime.now());
//		this.noticeRepository.save(n1);
//
//		Notice n2 = new Notice();
//		n2.setTitle("공지사항2");
//		n2.setCategory("전체");
//		n2.setContent("전체 공지사항2");
//		n2.setCreateDate(LocalDateTime.now());
//		this.noticeRepository.save(n2);
//
//	}


}
