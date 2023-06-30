package com.example.bookings.service;

import com.example.bookings.model.*;
import com.example.bookings.repository.SeatRepository;
import com.example.bookings.repository.SegmentWagonStatusRepository;
import com.example.bookings.repository.WagonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SeatService {
    private final SeatRepository seatRepository;


    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat getSeatById(Long id) {
        return seatRepository.findById(id).orElse(null);
    }

    public Seat updateSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public void deleteSeat(Long id) {
        seatRepository.deleteById(id);
    }


    public void createSeatsForWagon(Wagon wagon, Route route, Train train) {
        for (int i = 0; i < wagon.getSeatCount(); i++) {
            Seat seat = new Seat();
            seat.setNumber("A " + " R-" + route.getId() + " T-" + train.getTrainId() + " W-" + wagon.getId() + "- S-" + i);
            seat.setWagon(wagon);
            seat.setStatusSeat(Status.AVAILABLE);
            seat.setTrain(train);
            seat.setRoute(route);

            seatRepository.save(seat);
        }
    }

    public Seat getSeatByNumberTicket(String seatNumber) {
        return seatRepository.findByNumber(seatNumber);
    }


}