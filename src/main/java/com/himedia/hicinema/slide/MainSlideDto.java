package com.himedia.hicinema.slide;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MainSlideDto {

    private long id;
    private String imgId;
    private String videoId;
    private LocalDateTime regDate;
}

