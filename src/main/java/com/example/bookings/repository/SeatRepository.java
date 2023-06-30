    package com.example.bookings.repository;

    import com.example.bookings.model.Seat;
    import com.example.bookings.model.SegmentWagonStatus;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.List;

    @Repository
    public interface SeatRepository extends JpaRepository<Seat, Long> {
        Seat findByNumber(String seatNumber);
    }