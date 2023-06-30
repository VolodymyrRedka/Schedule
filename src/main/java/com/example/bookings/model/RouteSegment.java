package com.example.bookings.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "route_segment")
@Data
@Component
public class RouteSegment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "route_id")
    @JsonBackReference
    private Route route;


    @ManyToOne
    @JoinColumn(name = "departure_station_id")
    private Station departureStation;


    @ManyToOne
    @JoinColumn(name = "arrival_station_id")
    private Station arrivalStation;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "distance_km")
    private String distance;

    @Column(name = "duration_minutes")
    private String duration;

    @Column(name = "stop_duration_minutes")
    private int stopDuration;

    @ToString.Exclude
    @OneToMany(mappedBy = "segment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SegmentWagonStatus> wagonStatuses = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "train_id")
    @JsonManagedReference
    private Train train;

    @Column(name = "departure_station_name")
    private String departureStationName;

    @Column(name = "arrival_station_name")
    private String arrivalStationName;

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public double getDistanceValue() {
        String distanceString = getDistance();
        String distanceValueString = distanceString.replace(" км", "").replace(",", ".");
        return Double.parseDouble(distanceValueString);
    }

    public void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
        this.departureStationName = departureStation.getName();
    }

    public void setArrivalStation(Station arrivalStation) {
        this.arrivalStation = arrivalStation;
        this.arrivalStationName = arrivalStation.getName();
    }

}