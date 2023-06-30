package com.example.bookings.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "station")
@Data
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Long stationId;

    @Column(name = "name")
    private String name;

    @Column(name = "coordinates")
    private String coordinates;

    @Column(name = "stop_duration")
    private int stopDuration;

}
