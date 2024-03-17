package com.himedia.hicinema.movie.schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ScheduleRepositoryCustom {
    List<Map<String, Object>> findUserPageSchedule(String movieCd, LocalDateTime screeningDate, Long theaterId);
}
