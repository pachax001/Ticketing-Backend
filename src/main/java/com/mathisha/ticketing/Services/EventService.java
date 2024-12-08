package com.mathisha.ticketing.Services;


import com.mathisha.ticketing.DTO.EventDetailsResponse;
import com.mathisha.ticketing.DTO.VendorEventStatsResponse;
import com.mathisha.ticketing.Enums.TicketStatus;
import com.mathisha.ticketing.Exceptions.EventNotFoundException;
import com.mathisha.ticketing.Exceptions.NotAuthorizedException;
import com.mathisha.ticketing.Exceptions.UserNotFoundException;
import com.mathisha.ticketing.Models.Event;
import com.mathisha.ticketing.Models.Ticket;
import com.mathisha.ticketing.Models.Vendor;
import com.mathisha.ticketing.Repositories.EventRepository;
import com.mathisha.ticketing.Repositories.TicketRepository;
import com.mathisha.ticketing.Repositories.VendorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private VendorRepository vendorRepository;

    public Event addEvent(String title, String description, UUID vendorId) {
        // Validate vendor
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found"));

        if (!vendor.getRole().equals(com.mathisha.ticketing.Enums.Roles.VENDOR)) {
            throw new NotAuthorizedException("User is not authorized to add events");
        }

        // Create event
        Event event = new Event();
        event.setTitle(title);
        event.setDescription(description);
        event.setVendor(vendor);

        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    public List<VendorEventStatsResponse> getVendorEventStats(UUID vendorId) {
        return eventRepository.findEventStatsByVendor(vendorId);
    }
    @Transactional
    public void deleteEvent(UUID vendorId, UUID eventId) {
        // Retrieve the event and validate its existence
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        // Check if the vendor is authorized to delete the event
        if (!event.getVendor().getId().equals(vendorId)) {
            throw new NotAuthorizedException("Unauthorized to delete this event");
        }

        // Delete the event (cascade takes care of associated tickets)
        eventRepository.delete(event);
    }
    public EventDetailsResponse getEventDetails(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        UUID vendorId = event.getVendor().getId();
        String vendorName = event.getVendor().getFirstName() + " " + event.getVendor().getLastName();

        int totalTickets = ticketRepository.countByEventId(eventId);
        int availableTickets = ticketRepository.countByEventIdAndStatus(eventId, TicketStatus.AVAILABLE);

        // Assuming all tickets have the same price
        String ticketPrice = ticketRepository.findFirstByEventId(eventId)
                .map(Ticket::getPrice)
                .orElse("N/A");

        return new EventDetailsResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                vendorId,
                vendorName,
                totalTickets,
                availableTickets,
                ticketPrice
        );
    }

    public List<EventDetailsResponse> getEventsByVendor(UUID vendorId) {
        // Fetch all events for the given vendor
        List<Event> events = eventRepository.findEventsByVendor(vendorId);

        // Map each Event to an EventDetailsResponse
        List<EventDetailsResponse> eventDetailsResponses = events.stream().map(event -> {
            UUID eventId = event.getId();
            String vendorName = event.getVendor().getFirstName() + " " + event.getVendor().getLastName();

            int totalTickets = ticketRepository.countByEventId(eventId);
            int availableTickets = ticketRepository.countByEventIdAndStatus(eventId, TicketStatus.AVAILABLE);

            // Assuming all tickets for an event have the same price
            String ticketPrice = ticketRepository.findFirstByEventId(eventId)
                    .map(Ticket::getPrice)
                    .orElse("N/A");

            // Create and return EventDetailsResponse for each event
            return new EventDetailsResponse(
                    event.getId(),
                    event.getTitle(),
                    event.getDescription(),
                    vendorId,
                    vendorName,
                    totalTickets,
                    availableTickets,
                    ticketPrice
            );
        }).toList();

        return eventDetailsResponses;
    }



}
