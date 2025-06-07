package com.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.model.Booking;
import com.demo.model.Trip;
import com.demo.repository.BookingRepository;
import com.demo.repository.TripRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepo;
    private final TripRepository tripRepo;

    public Booking saveBooking(Booking booking, Long tripId) {
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        booking.setTrip(trip);
        return bookingRepo.save(booking);
    }

    public List<Booking> getBookingsForTrip(Long tripId) {
        return bookingRepo.findByTripId(tripId);
    }
    
    public void deleteBookingsByTripId(Long tripId) {
        List<Booking> bookings = bookingRepo.findByTripId(tripId);
        if (!bookings.isEmpty()) {
            bookingRepo.deleteAll(bookings);
        }
    }
}


