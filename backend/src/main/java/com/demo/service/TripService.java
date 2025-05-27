package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Trip;
import com.demo.repository.TripRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripService {
   private final TripRepository tripRepository;
   @Autowired
   private  GeoService geoService;
   
   public Trip saveTrip(Trip trip) {
	   return tripRepository.save(trip);
   }
   
   public List<Trip> getTripsByUser(String email){
	   return tripRepository.findByUserEmail(email);
   }
   
   public void deleteTrip(Long id) {
	    tripRepository.deleteById(id);
	}

   public Trip getTrip(Long id) {
	    return tripRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Trip not found"));
	}

	public Trip updateTrip(Long id, Trip updatedTrip) {
	    Trip existing = tripRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Trip not found"));

	    existing.setDestination(updatedTrip.getDestination());
	    existing.setStartDate(updatedTrip.getStartDate());
	    existing.setEndDate(updatedTrip.getEndDate());
	    existing.setBudget(updatedTrip.getBudget());

	    return tripRepository.save(existing);
	}

	public Trip saveTrip(Trip trip, String userEmail) throws Exception {
	    double[] coords = geoService.getCoordinates(trip.getDestination());
	    System.out.println("✅ Coordinates: " + coords[0] + ", " + coords[1]);

	    trip.setLat(coords[0]);
	    trip.setLng(coords[1]);
	    trip.setUserEmail(userEmail); // ✅ Don't forget to assign user email!

	    return tripRepository.save(trip);
	}


}
