package com.example.bookings.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "wagon")
@Data
public class Wagon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "wagon_status")
    private Status statusWagon;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id")
    @JsonIgnoreProperties("wagonStatus")
    private Train train;

    @OneToMany(mappedBy = "wagon", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("wagon")
    private List<Seat> seats;

    @Column(name = "seat_count")
    private int seatCount;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    @JsonIgnore
    private Route route;

}