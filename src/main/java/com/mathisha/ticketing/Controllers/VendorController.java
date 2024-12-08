package com.mathisha.ticketing.Controllers;

import com.mathisha.ticketing.DTO.EventDetailsResponse;
import com.mathisha.ticketing.DTO.VendorEventStatsResponse;
import com.mathisha.ticketing.Services.EventService;
import com.mathisha.ticketing.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vendor")
public class VendorController {

    @Autowired
    private EventService eventService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/{vendorId}/events/stats")
    public ResponseEntity<List<VendorEventStatsResponse>> getVendorEventStats(@PathVariable UUID vendorId) {
        List<VendorEventStatsResponse> stats = eventService.getVendorEventStats(vendorId);
        return ResponseEntity.ok(stats);
    }
    @DeleteMapping("/{vendorId}/event/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable UUID vendorId, @PathVariable UUID eventId) {
        eventService.deleteEvent(vendorId, eventId);
        return ResponseEntity.ok("Event deleted successfully.");
    }

    @DeleteMapping("/tickets-delete/{vendorId}/event/{eventId}")
    public ResponseEntity<String> deleteAvailableTickets(
            @PathVariable UUID vendorId,
            @PathVariable UUID eventId,
            @RequestParam int count
    ) {
        try {
            ticketService.deleteAvailableTickets(vendorId, eventId, count);
            return ResponseEntity.ok(count + " tickets deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{vendor_id}/events")
    public ResponseEntity<List<EventDetailsResponse>> getEventsByVendor(@PathVariable("vendor_id") UUID vendorId) {
        // Fetch events for the given vendor
        List<EventDetailsResponse> events = eventService.getEventsByVendor(vendorId);

        // Return the response
        return ResponseEntity.ok(events);
    }
}
