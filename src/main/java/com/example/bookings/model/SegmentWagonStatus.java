package com.example.bookings.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;

import org.springframework.stereotype.Component;


@Entity
@Table(name = "segment_wagon_status")
@Data
@Component
@JsonIdentityReference(alwaysAsId = true)
public class SegmentWagonStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "segment_id")
    @JsonIgnore
    private RouteSegment segment;

    @ManyToOne
    @JoinColumn(name = "wagon_status_id")
    private Wagon wagonStatus;

    @Column(name = "cost")
    private String cost;

    @Column(name = "wagon_status_name")
    @Enumerated(EnumType.STRING)
    private Status wagonStatusName;

    @Column(name = "segment_distance")
    private double segmentDistance;

    @Column(name = "train_status")
    @Enumerated(EnumType.STRING)
    private Status trainStatus;

}