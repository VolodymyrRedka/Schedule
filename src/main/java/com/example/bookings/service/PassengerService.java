package com.example.bookings.service;

import com.example.bookings.model.Passenger;
import com.example.bookings.model.Seat;
import com.example.bookings.repository.PassengerRepository;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Passenger createPassenger(String firstName, String lastName, String email, Seat seat) {
        Passenger passenger = new Passenger();
        passenger.setFirstName(firstName);
        passenger.setLastName(lastName);
        passenger.setEmail(email);
        passenger.setSeat(seat);

        return passengerRepository.save(passenger);
    }

}