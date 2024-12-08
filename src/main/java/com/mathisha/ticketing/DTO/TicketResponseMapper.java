package com.mathisha.ticketing.DTO;

import com.mathisha.ticketing.Models.Ticket;

public class TicketResponseMapper {

    public static TicketResponse mapToTicketResponse(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getEvent().getId(),
                ticket.getVendor().getId(),
                ticket.getPrice()
        );
    }
}
