package com.mathisha.ticketing.Controllers;

import com.mathisha.ticketing.DTO.EventDetailsResponse;
import com.mathisha.ticketing.DTO.EventRequest;
import com.mathisha.ticketing.DTO.EventResponse;
import com.mathisha.ticketing.DTO.EventResponseMapper;
import com.mathisha.ticketing.Models.Event;
import com.mathisha.ticketing.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/add")
    public ResponseEntity<EventResponse> addEvent(@Valid @RequestBody EventRequest eventRequest) {
        Event event = eventService.addEvent(
                eventRequest.getTitle(),
                eventRequest.getDescription(),
                eventRequest.getVendorId()
        );

        EventResponse eventResponse = EventResponseMapper.mapToEventResponse(event);
        return ResponseEntity.ok(eventResponse);
    }
    @GetMapping("/all")
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        List<EventResponse> eventResponses = EventResponseMapper.mapToEventResponses(events);
        return ResponseEntity.ok(eventResponses);
    }
    @GetMapping("id/{eventId}")
    public ResponseEntity<EventDetailsResponse> getEventDetails(@PathVariable UUID eventId) {
        EventDetailsResponse eventDetails = eventService.getEventDetails(eventId);
        return ResponseEntity.ok(eventDetails);
    }
}
