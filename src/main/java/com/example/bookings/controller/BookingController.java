package com.example.bookings.controller;

import com.example.bookings.model.*;
import com.example.bookings.service.PassengerService;
import com.example.bookings.service.SeatService;
import com.example.bookings.service.TicketService;
import com.example.bookings.service.WagonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class BookingController {

    private final PassengerService passengerService;
    private final TicketService ticketService;
    private final WagonService wagonService;
    private final SeatService seatService;

    public BookingController(SeatService seatService, PassengerService passengerService, TicketService ticketService, WagonService wagonService) {
        this.passengerService = passengerService;
        this.ticketService = ticketService;
        this.wagonService = wagonService;
        this.seatService = seatService;
    }

    // Метод, який обробляє POST-запит на створення пасажира
    @PostMapping("/passengers")
    public String createPassenger(@ModelAttribute Passenger passenger, @RequestParam("seatId") Long seatId, Model model) {
        Seat seat = seatService.getSeatById(seatId);

        if (seat.getStatusSeat() == Status.RESERVED || seat.getStatusSeat() == Status.PAID) {
            // Місце вже зарезервоване або оплачене, перенаправляємо на сторінку "seat-unavailable.html"
            return "seat-unavailable";
        }

        Passenger createdPassenger = passengerService.createPassenger(passenger.getFirstName(), passenger.getLastName(), passenger.getEmail(), seat);
        int passengerId = createdPassenger.getId();

        seat.setPassengerId(passengerId);
        seat.setStatusSeat(Status.RESERVED);
        seatService.updateSeat(seat);

        createdPassenger.setSeat(seat);
        model.addAttribute("passenger", createdPassenger);

        return "passenger-details";
    }

    // Метод, який обробляє GET-запит для відображення сторінки "seat-unavailable.html"
    @GetMapping("/seat-unavailable")
    public String showSeatUnavailablePage() {
        return "seat-unavailable";
    }
}