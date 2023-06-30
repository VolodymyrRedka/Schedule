package com.example.bookings.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "trainId")
@Table(name = "train")
@Data
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long trainId;

    @Enumerated(EnumType.STRING)
    @Column(name = "train_status")
    private Status statusTrain;

    @Column(name = "speed")
    private int speed;

    @ToString.Exclude
    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Route> routes;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "train")
    private List<Wagon> wagons;

    @ToString.Exclude
    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    private List<Seat> seats;

}
