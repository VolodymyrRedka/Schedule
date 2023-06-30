package com.example.bookings.repository;

import com.example.bookings.model.RouteSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteSegmentRepository extends JpaRepository<RouteSegment, Integer> {

}