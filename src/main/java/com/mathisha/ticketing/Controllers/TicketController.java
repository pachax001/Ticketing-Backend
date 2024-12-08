package com.mathisha.ticketing.Controllers;


import com.mathisha.ticketing.DTO.*;
import com.mathisha.ticketing.Models.Ticket;
import com.mathisha.ticketing.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/add")
    public ResponseEntity<String> addTicket(@RequestBody TicketRequest ticketRequest) {
        int count = ticketRequest.getCount();
        ticketService.addTickets(
                ticketRequest.getVendorId(),
                ticketRequest.getEventId(),
                ticketRequest.getPrice(),
                count
        );
        //TicketResponse response = TicketResponseMapper.mapToTicketResponse(ticket);
        return ResponseEntity.ok(count + " tickets generated successfully.");
    }




}
