package com.mathisha.ticketing.Controllers;

import com.mathisha.ticketing.DTO.BuyTicketRequest;
import com.mathisha.ticketing.DTO.CustomerEventResponse;
import com.mathisha.ticketing.DTO.CustomerTicketResponse;
import com.mathisha.ticketing.DTO.CustomerTicketResponseMapper;
import com.mathisha.ticketing.Models.Ticket;
import com.mathisha.ticketing.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/{customerId}/event/{eventId}/count")
    public ResponseEntity<Long> getTicketsBoughtForEvent(
            @PathVariable UUID customerId,
            @PathVariable UUID eventId) {
        long ticketCount = ticketService.getTicketsBoughtByCustomerForEvent(customerId, eventId);
        return ResponseEntity.ok(ticketCount);
    }

    @GetMapping("/{customerId}/events")
    public ResponseEntity<List<CustomerEventResponse>> getCustomerEvents(@PathVariable UUID customerId) {
        List<CustomerEventResponse> events = ticketService.getCustomerEventsWithTickets(customerId);
        return ResponseEntity.ok(events);
    }
    @PostMapping("/buy")
    public ResponseEntity<String> buyTicket(@RequestBody BuyTicketRequest buyTicketRequest) {
        UUID customerId = buyTicketRequest.getCustomerId();
        UUID eventId = buyTicketRequest.getEventId();
        int count = buyTicketRequest.getCount();
        ticketService.buyTickets(customerId, eventId, count);
        return ResponseEntity.ok(count + " tickets purchased successfully.");

    }
}
