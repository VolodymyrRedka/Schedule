package com.example.bookings.service;

import com.example.bookings.model.Ticket;
import com.example.bookings.repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void createTicket(Ticket ticket) {

        ticketRepository.save(ticket);
    }


}