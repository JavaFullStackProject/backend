package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.model.Booking;

import jakarta.transaction.Transactional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{
	List<Booking> findByTripId(Long tripId);
	@Modifying
	@Transactional
	@Query("DELETE FROM Booking b WHERE b.trip.id = :tripId")
	void deleteByTripId(@Param("tripId") Long tripId);
}