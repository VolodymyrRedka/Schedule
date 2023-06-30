    package com.example.bookings.model;

    import com.fasterxml.jackson.annotation.*;
    import jakarta.persistence.*;
    import lombok.Data;
    import lombok.ToString;

    import java.time.LocalDateTime;
    import java.util.*;

    @Entity
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Table(name = "route")
    @Data
    public class Route {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "name")
        private String name;

        @ToString.Exclude
        @ManyToOne
        @JoinColumn(name = "train_id")
        //@JsonManagedReference
        @JsonIgnore
        //@Transient
        private Train train;

        @Enumerated(EnumType.STRING)
        @Column(name = "train_status")
        private Status statusTrain;

        @Column(name = "arrival_time_first")
        private LocalDateTime arrivalTimeFirst;

        @Column(name = "arrival_time_final")
        private LocalDateTime arrivalTimeFinal;

        @Column(name = "total_distance_km")
        private String totalDistance;

        @Column(name = "total_duration_hour")
        private String totalDuration;

        @ToString.Exclude
        @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
        @JsonIdentityReference(alwaysAsId = true)
        private List<Wagon> wagons;

        @JsonIgnore
        @ToString.Exclude
        @OneToMany(mappedBy = "route", fetch = FetchType.LAZY)
        private List<Seat> seats;

        @Column(name = "first_station_name")
        private String firstStationName;

        @Column(name = "last_station_name")
        private String lastStationName;

        @JsonManagedReference
        @ToString.Exclude
        @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
        private List<RouteSegment> segments;

        @Transient
        @JsonIgnore
        Map<RouteSegment, List<Status>> wagonStatusMap = new LinkedHashMap<>();

        public Route() {
            this.seats = new ArrayList<>();
            this.segments = new ArrayList<>();
            this.wagonStatusMap = new HashMap<>();
        }

        public void setTotalDuration(long totalDurationInHours) {
            long hours = totalDurationInHours / 60;
            long minutes = totalDurationInHours % 60;
            this.totalDuration = hours + " год " + minutes + " хв";
        }

    }
