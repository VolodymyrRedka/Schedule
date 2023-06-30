package com.example.bookings.controller;

import com.example.bookings.model.*;
import com.example.bookings.service.PassengerService;
import com.example.bookings.service.SeatService;
import com.example.bookings.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final PassengerService passengerService;
    private final SeatService seatService;

    public TicketController(SeatService seatService, TicketService ticketService, PassengerService passengerService) {
        this.ticketService = ticketService;
        this.passengerService = passengerService;
        this.seatService = seatService;
    }

    @PostMapping
    public ModelAndView createTicket(@RequestParam("segmentId") Long segmentId,
                                     @RequestParam("segmentDepartureStation") String segmentDepartureStation,
                                     @RequestParam("segmentArrivalStation") String segmentArrivalStation,
                                     @RequestParam("selectedSeat") String selectedSeat,
                                     @RequestParam("routeName") String routeName,
                                     @RequestParam("trainId") Long trainId,
                                     @RequestParam("trainStatus") String trainStatus,
                                     @RequestParam("departureTime") LocalDateTime departureTime,
                                     @RequestParam("distance") String distance,
                                     @RequestParam("duration") String duration,
                                     @RequestParam("wagonId") Long wagonId,
                                     @RequestParam("wagonStatus") String wagonStatus,
                                     @RequestParam("fare") String fare,
                                     @RequestParam("firstName") String firstName,
                                     @RequestParam("lastName") String lastName,
                                     @RequestParam("email") String email) {

        // Створити пасажира
        Seat seat = seatService.getSeatByNumberTicket(selectedSeat);
        // Перевірити статус місця
        if (seat.getStatusSeat() == Status.RESERVED || seat.getStatusSeat() == Status.PAID) {
            return new ModelAndView("seat-unavailable"); // повернути відповідне представлення або повідомлення про недоступність місця
        }

        Passenger passenger = passengerService.createPassenger(firstName, lastName, email, seat);

        // Створити квиток
        Ticket ticket = new Ticket();
        ticket.setSegmentId(segmentId);
        ticket.setSegmentDepartureStation(segmentDepartureStation);
        ticket.setSegmentArrivalStation(segmentArrivalStation);
        ticket.setSelectedSeat(selectedSeat);
        ticket.setRouteName(routeName);
        ticket.setTrainId(trainId);
        ticket.setTrainStatus(trainStatus);
        ticket.setDepartureTime(departureTime);
        ticket.setDistance(distance);
        ticket.setDuration(duration);
        ticket.setWagonId(wagonId);
        ticket.setWagonStatus(wagonStatus);
        ticket.setFare(fare);
        ticket.setPassengerId(passenger);

        ticketService.createTicket(ticket);
        // Оновити статус місця
        seat.setPassengerId(passenger.getId());
        seat.setStatusSeat(Status.PAID);
        seatService.updateSeat(seat);

        ModelAndView modelAndView = new ModelAndView("ticket-details");
        modelAndView.addObject("ticket", ticket); // Додати об'єкт квитка до моделі

        return modelAndView;
    }
}