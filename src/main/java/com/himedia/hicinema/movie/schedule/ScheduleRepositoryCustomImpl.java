package com.himedia.hicinema.movie.schedule;

import com.himedia.hicinema.movie.theater.Theater;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ScheduleRepositoryCustomImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Map<String, Object>> findUserPageSchedule(String movieCd, LocalDateTime screeningDate, Long theaterId) {
        String sql =
                "SELECT sch.*, mv.title, mv.rats, sc.name, sc.seat, (select count(*) from seat where schedule_id = sch.schedule_id and status = 'P') as selected_seat  FROM schedule sch left join movie mv on sch.movie_cd = mv.movie_cd " +
                        "left join screen sc on sch.screen_id = sc.screen_id " +
                        "where sc.theater_id = :theaterId and mv.movie_cd = :movieCd " +
                        "and to_char(sch.screening_date, 'YYYY-MM-DD') = to_char(:screeningDate, 'YYYY-MM-DD') " +
                        "order by sch.start_time asc";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("theaterId", theaterId);
        paramMap.put("movieCd", movieCd);
        paramMap.put("screeningDate", screeningDate); // 문자열로 변환된 날짜 전달

//        List<Map<String, Object>> result = namedParameterJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<>(Schedule.class));
        return namedParameterJdbcTemplate.queryForList(sql, paramMap);
    }


}
