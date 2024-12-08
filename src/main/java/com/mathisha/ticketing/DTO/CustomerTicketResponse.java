package com.mathisha.ticketing.DTO;

import java.util.UUID;

public class CustomerTicketResponse {
    private UUID eventId;
    private String eventTitle;
    private String description;
    private String price;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CustomerTicketResponse(UUID eventId, String eventTitle, String description, String price) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.description = description;
        this.price = price;
    }

    // Getters and setters
    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
