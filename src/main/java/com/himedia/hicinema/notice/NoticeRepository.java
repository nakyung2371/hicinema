package com.himedia.hicinema.notice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    Notice findByTitleLike(String title);
    Notice findByContentLike(String content);

    //List<Notice> nList = this.noticeRepository.findByTitleLike("aaa%");
    //Notice n = nList.get(0);
    //assertEquals("", n.getTitle());
}
