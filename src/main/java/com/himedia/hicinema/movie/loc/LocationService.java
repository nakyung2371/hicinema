package com.himedia.hicinema.movie.loc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LocationService {
	private final LocationRepository locationRepository;

	public List<Location> getList(String status) {
		return locationRepository.findByStatusOrderByIdAsc(status);
	}

	public void create(Location loc) {
		locationRepository.save(loc);
	}

	public void delete(Location loc) {
		locationRepository.save(loc);
	}

	public Optional<Location> getLocation(Long id, String status) {
		return locationRepository.findLocationByIdAndStatus(id, status);
	}
}
