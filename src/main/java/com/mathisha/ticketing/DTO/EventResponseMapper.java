package com.mathisha.ticketing.DTO;

import com.mathisha.ticketing.Models.Event;

import java.util.List;
import java.util.stream.Collectors;

public class EventResponseMapper {

    // Map a single Event to EventResponse
    public static EventResponse mapToEventResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getVendor().getId() // Include only vendor ID
        );
    }

    // Map a list of Events to a list of EventResponses
    public static List<EventResponse> mapToEventResponses(List<Event> events) {
        return events.stream()
                .map(EventResponseMapper::mapToEventResponse)
                .collect(Collectors.toList());
    }
}
