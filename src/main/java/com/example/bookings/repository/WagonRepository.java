package com.example.bookings.repository;

import com.example.bookings.model.Status;
import com.example.bookings.model.Wagon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WagonRepository extends JpaRepository<Wagon, Long> {
}
