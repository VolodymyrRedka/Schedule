package com.example.bookings.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Ticket")
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "segment_id")
    private Long segmentId;

    @Column(name = "segment_departure_station")
    private String segmentDepartureStation;

    @Column(name = "segment_arrival_station")
    private String segmentArrivalStation;

    @Column(name = "selected_seat")
    private String selectedSeat;

    @Column(name = "route_name")
    private String routeName;

    @Column(name = "train_id")
    private Long trainId;

    @Column(name = "train_status")
    private String trainStatus;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "distance")
    private String distance;

    @Column(name = "duration")
    private String duration;

    @Column(name = "wagon_id")
    private Long wagonId;

    @Column(name = "wagon_status")
    private String wagonStatus;

    @Column(name = "fare")
    private String fare;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passengerId;
}