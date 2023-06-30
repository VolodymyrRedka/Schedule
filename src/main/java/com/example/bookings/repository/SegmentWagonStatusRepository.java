package com.example.bookings.repository;

import com.example.bookings.model.SegmentWagonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SegmentWagonStatusRepository extends JpaRepository<SegmentWagonStatus, Integer> {

}