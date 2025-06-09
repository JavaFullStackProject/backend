package com.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // "hotel" or "flight"
    private String reference;
    private String provider;
    private String details;

    private LocalDate startDate;
    private LocalDate endDate;
    private String location; // e.g., city
    private Double estimatedCost;
    private Double actualCost;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
}
