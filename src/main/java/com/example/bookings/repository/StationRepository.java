package com.example.bookings.repository;

import com.example.bookings.model.Route;
import com.example.bookings.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer> {
    Station findByName(String name);
}
