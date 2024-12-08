package com.mathisha.ticketing.DTO;

import com.mathisha.ticketing.Models.Ticket;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerTicketResponseMapper {
    public static List<CustomerTicketResponse> mapToCustomerTicketResponse(List<Ticket> tickets) {
        return tickets.stream()
                .map(ticket -> new CustomerTicketResponse(
                        ticket.getEvent().getId(),
                        ticket.getEvent().getTitle(),
                        ticket.getEvent().getDescription(),
                        ticket.getPrice()
                ))
                .collect(Collectors.toList());
    }
}
