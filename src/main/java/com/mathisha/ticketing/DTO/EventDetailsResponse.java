package com.mathisha.ticketing.DTO;

import java.util.UUID;

public class EventDetailsResponse {
    private UUID eventId;
    private String title;
    private String description;
    private UUID vendorId;
    private String vendorName;
    private int totalTickets;
    private int availableTickets;
    private String ticketPrice;

    // Constructor
    public EventDetailsResponse(UUID eventId, String title, String description, UUID vendorId, String vendorName, int totalTickets, int availableTickets, String ticketPrice) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.totalTickets = totalTickets;
        this.availableTickets = availableTickets;
        this.ticketPrice = ticketPrice;
    }

    // Getters and Setters
    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getVendorId() {
        return vendorId;
    }

    public void setVendorId(UUID vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
