package com.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Trip;
import com.demo.security.JwtUtil;
import com.demo.service.TripService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {
    
	private final TripService tripService;
	private final JwtUtil jwtUtil;
	
	@PostMapping
	public ResponseEntity<Trip> saveTrip(@RequestBody Trip trip, HttpServletRequest request) throws Exception {
	    String authHeader = request.getHeader("Authorization");
	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        throw new RuntimeException("Missing or invalid Authorization header");
	    }

	    String token = authHeader.substring(7);
	    String email = jwtUtil.extractEmail(token);

	    System.out.println("📍 Saving trip to: " + trip.getDestination() + " for " + email);

	    // ✅ Pass email to service
	    return ResponseEntity.ok(tripService.saveTrip(trip, email));
	}

	
	@GetMapping
	public ResponseEntity<List<Trip>> getTrips(HttpServletRequest request){
		String token = request.getHeader("Authorization").substring(7);
		String email = jwtUtil.extractEmail(token);
		
		return ResponseEntity.ok(tripService.getTripsByUser(email));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
	    tripService.deleteTrip(id);
	    return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Trip> getTrip(@PathVariable Long id) {
	    return ResponseEntity.ok(tripService.getTrip(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @RequestBody Trip updatedTrip) throws Exception {
	    return ResponseEntity.ok(tripService.updateTrip(id, updatedTrip));
	}

}
