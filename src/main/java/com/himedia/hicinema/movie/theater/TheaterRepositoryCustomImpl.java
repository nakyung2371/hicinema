package com.himedia.hicinema.movie.theater;

import com.himedia.hicinema.movie.Screen.QScreen;
import com.himedia.hicinema.movie.loc.QLocation;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TheaterRepositoryCustomImpl implements TheaterRepositoryCustom {

	private JPAQueryFactory queryFactory;

	public TheaterRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	public BooleanExpression searchLocation(TheaterSearchDto tsd) {
		if(tsd.getLocation() != null && tsd.getLocation().getId() > 0) {
			return QTheater.theater.location.id.eq(tsd.getLocation().getId());
		}

		return null;
	}

	public BooleanExpression searchTheaterName(TheaterSearchDto tsd) {
		if(tsd.getName() != null) {
			return QTheater.theater.name.like("%" + tsd.getName() + "%");
		}

		return null;
	}


	public Page<TheaterList> getAdminTheaterList(TheaterSearchDto tsd, Pageable pageable) {

		log.info("========= TheaterSearchDto 값 출력 - 시작 ==========================");
		log.info(tsd.toString());
		log.info(String.valueOf(pageable.getOffset()));
		log.info(String.valueOf(pageable.getPageSize()));
		log.info("=========TheaterSearchDto 값 출력 - 끝 ==========================");
		QTheater theater = QTheater.theater;
		QLocation location = QLocation.location;
		QScreen screen = QScreen.screen;

//		List<TheaterList> content = new ArrayList<>();
		List<TheaterList> content = queryFactory
				.select(
						new QTheaterList(
								theater.id,
								theater.location,
								theater.name,
								theater.zipcode,
								theater.addr,
								theater.addr_detail,
								theater.addr_ref
								,
								(queryFactory
										.select(Wildcard.count)
										.from(screen)
										.where(screen.status.eq("O"))
										.where(screen.theater.id.eq(theater.id)))
//								,
//								(queryFactory
//										.select(screen.seat.sum())
//										.from(screen)
//										.where(screen.theater.id.eq(theater.id))
//								)
						)
				)
				.from(theater)
				.where(theater.status.eq(tsd.getStatus()))
				.where(searchLocation(tsd))
				.where(searchTheaterName(tsd))
				.orderBy(theater.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();


		long total = queryFactory
				.select(Wildcard.count)
				.from(theater)
				.where(theater.status.eq(tsd.getStatus()))
				.where(searchLocation(tsd))
				.where(searchTheaterName(tsd))
				.fetchOne();
		return new PageImpl<>(content, pageable, total);
	}
}
