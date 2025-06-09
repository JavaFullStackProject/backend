package com.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Booking;
import com.demo.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> addBooking(@RequestBody Booking booking, @RequestParam Long tripId) {
        return ResponseEntity.ok(bookingService.saveBooking(booking, tripId));
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getBookings(@RequestParam Long tripId) {
        return ResponseEntity.ok(bookingService.getBookingsForTrip(tripId));
    }
    
    @DeleteMapping
    public ResponseEntity<String> deleteBookings(@RequestParam Long tripId) {
        bookingService.deleteBookingsByTripId(tripId);
        return ResponseEntity.ok("Bookings for trip ID " + tripId + " deleted successfully.");
    }
    
}

