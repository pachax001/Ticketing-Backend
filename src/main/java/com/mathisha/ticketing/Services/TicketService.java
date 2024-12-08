package com.mathisha.ticketing.Services;

import com.mathisha.ticketing.DTO.CustomerEventResponse;
import com.mathisha.ticketing.Enums.TicketStatus;
import com.mathisha.ticketing.Exceptions.*;
import com.mathisha.ticketing.Models.Customer;
import com.mathisha.ticketing.Models.Event;
import com.mathisha.ticketing.Models.Ticket;
import com.mathisha.ticketing.Models.Vendor;
import com.mathisha.ticketing.Repositories.CustomerRepository;
import com.mathisha.ticketing.Repositories.EventRepository;
import com.mathisha.ticketing.Repositories.TicketRepository;
import com.mathisha.ticketing.Repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private EventRepository eventRepository;


    @Transactional
    public void addTickets(UUID vendorId, UUID eventId, String price, int count) {

        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new UserNotFoundException("Vendor not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found"));

        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Ticket ticket = new Ticket();
            ticket.setVendor(vendor);
            ticket.setEvent(event);
            ticket.setPrice(price);
            ticket.setStatus(TicketStatus.AVAILABLE);
            tickets.add(ticket);

            // Periodic saving to avoid memory issues for huge counts
            if (tickets.size() % 1000 == 0) {
                ticketRepository.saveAll(tickets);
                tickets.clear();
            }

        }
        if (!tickets.isEmpty()) {
            ticketRepository.saveAll(tickets);
        }

        //return tickets;
    }

    public void buyTickets(UUID customerId, UUID eventId, int count) {
        List<Ticket> availableTickets = ticketRepository.findStatusOfTicketsForGivenEvent(eventId, TicketStatus.AVAILABLE);
        if (availableTickets.size()<count) {
            throw new NotEnoughTicketsException("Not enough available tickets");

        }

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new UserNotFoundException("Customer not found"));

        List<Ticket> ticketsToSell = availableTickets.subList(0, count);

        ticketsToSell.forEach(ticket -> {
            ticket.setStatus(TicketStatus.SOLD_OUT);
            ticket.setCustomer(customer);
            ticket.setIssuedAt(LocalDateTime.now());
        });
        ticketRepository.saveAll(ticketsToSell);
    }

    public List<CustomerEventResponse> getCustomerEventsWithTickets(UUID customerId) {
        return ticketRepository.findCustomerEventsWithTicketCount(customerId);
    }
    public long getTicketsBoughtByCustomerForEvent(UUID customerId, UUID eventId) {
        return ticketRepository.countTicketsByCustomerAndEvent(customerId, eventId);
    }

    @Transactional
    public void deleteAvailableTickets(UUID vendorId, UUID eventId, int count) {
        // Validate that the event exists and belongs to the vendor
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        if (!event.getVendor().getId().equals(vendorId)) {
            throw new NotAuthorizedException("Unauthorized to delete tickets for this event");
        }

        // Fetch available tickets
        List<Ticket> tickets = ticketRepository.findAvailableTicketsByEventAndVendor(
                eventId,
                vendorId,
                PageRequest.of(0, count) // Limit the number of tickets fetched
        );

        if (tickets.size() < count) {
            throw new NotEnoughTicketsException("Not enough available tickets to delete");
        }

        // Collect ticket IDs for deletion
        List<UUID> ticketIds = tickets.stream()
                .map(Ticket::getId)
                .toList();

        // Delete the tickets
        ticketRepository.deleteTicketsByIds(ticketIds);
    }

}