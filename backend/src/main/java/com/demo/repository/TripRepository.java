package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Trip;

public interface TripRepository extends JpaRepository<Trip, Long>{
    List<Trip> findByUserEmail(String userEmail);
}
