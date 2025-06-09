package com.demo.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.demo.model.Trip;
import com.demo.repository.TripRepository;

@Service
public class NotificationService {

    private final TripRepository tripRepository;
    private List<String> upcomingTripNotifications = new ArrayList<>();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public NotificationService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<String> getNotifications() {
        return new ArrayList<>(upcomingTripNotifications);
    }

    @Scheduled(fixedRate = 30000)
    public void checkUpcomingTrips() {
        LocalDate today = LocalDate.now();
        List<Trip> trips = tripRepository.findAll();
        List<String> alerts = new ArrayList<>();

        System.out.println("🔍 Checking trips on " + today);
        for (Trip trip : trips) {
            try {
                String startDateStr = trip.getStartDate(); // assuming String in format "yyyy-MM-dd"
                if (startDateStr == null || startDateStr.isEmpty()) {
                    System.out.println("⚠️ Skipping trip with empty date: " + trip.getDestination());
                    continue;
                }

                LocalDate startDate = LocalDate.parse(startDateStr, formatter);
                long daysUntil = ChronoUnit.DAYS.between(today, startDate);

                System.out.println("➡️ Trip to " + trip.getDestination() + " starts in " + daysUntil + " days");

                if (daysUntil >= 0 && daysUntil <= 3) {
                    alerts.add("📅 Reminder: Your trip to " + trip.getDestination()
                            + " starts in " + daysUntil + " day(s)!");
                }
            } catch (Exception e) {
                System.err.println("❌ Error parsing start date for trip: " + e.getMessage());
            }
        }

        this.upcomingTripNotifications = alerts;
        System.out.println("✅ Total alerts: " + alerts.size());
    }
}
