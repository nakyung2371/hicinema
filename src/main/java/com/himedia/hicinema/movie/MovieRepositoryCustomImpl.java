package com.himedia.hicinema.movie;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class MovieRepositoryCustomImpl implements MovieRepositoryCustom {

	private JPAQueryFactory queryFactory;

	public MovieRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	private BooleanExpression searchByLike(String searchBy, String searchQuery){

		if(StringUtils.equals("title", searchBy)){
			return QMovie.movie.title.like("%" + searchQuery + "%");
		} else if(StringUtils.equals("genre", searchBy)){
			return QMovie.movie.genre.like("%" + searchQuery + "%");
		}

		return null;
	}

	private BooleanExpression movieType(String type){

		if(type.equals("now")) {
			return (QMovie.movie.release.before(LocalDateTime.now())).and(QMovie.movie.screenOutDate.after(LocalDateTime.now()));
		} else if(type.equals("soon")) {
			return QMovie.movie.release.after(LocalDateTime.now()).and(QMovie.movie.screenOutDate.after(LocalDateTime.now()));
		} else if(type.equals("end")) {
			return QMovie.movie.screenOutDate.before(LocalDateTime.now());
		}

		return null;
	}

	@Override
	public Page<ListMovieDto> getAdminMoviePage(MovieSearchDto mv, Pageable pageable) {

		log.info("========= MovieSearchDto 값 출력 - 시작 ==========================");
		log.info(mv.getTitle());
		log.info(mv.getGenre());
		log.info("=========ItemSearchDto 값 출력 - 끝 ==========================");
		QMovie movie = QMovie.movie;

		List<ListMovieDto> content = queryFactory
				.select(
					new QListMovieDto(
							movie.movieCd,
							movie.title,
							movie.genre,
							movie.rats,
							movie.runtime,
							movie.release)
				)
				.from(movie)
				.where(searchByLike("title", mv.getTitle()))
				.where(searchByLike("genre", mv.getGenre()))
				.where(movie.status.eq(mv.getStatus()))
				.where(movie.screenOutDate.after(LocalDateTime.now()))
				.orderBy(movie.regDate.asc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();

		//검색한 레코드 총 갯수
		long total = queryFactory
				.select(Wildcard.count)
				.from(movie)
				.where(searchByLike("title", mv.getTitle()))
				.where(searchByLike("genre", mv.getGenre()))
				.where(movie.status.eq(mv.getStatus()))
				.where(movie.screenOutDate.after(LocalDateTime.now()))
				.fetchOne();

		return new PageImpl<>(content, pageable, total);
	}

	@Override
	public List<Movie> getUserMoviePage(MovieSearchDto mv) {

		log.info("========= MovieSearchDto 값 출력 - 시작 ==========================");
		log.info(mv.getStatus());
		log.info(mv.getType());
		log.info("=========ItemSearchDto 값 출력 - 끝 ==========================");
		QMovie movie = QMovie.movie;

		List<Movie> content = queryFactory
				.selectFrom(movie)
				.where(movie.status.eq(mv.getStatus()))
				.where(movieType(mv.getType()))
				.orderBy(movie.regDate.asc())   // 나중에 추가 필요
				.fetch();

		return content;
	}

}
