package com.mathisha.ticketing.DTO;

import java.util.UUID;

public class TicketResponse {
    private UUID id;
    private UUID eventId;
    private UUID vendorId;
    private String price;

    public TicketResponse(UUID id, UUID eventId, UUID vendorId, String price) {
        this.id = id;
        this.eventId = eventId;
        this.vendorId = vendorId;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public UUID getVendorId() {
        return vendorId;
    }

    public void setVendorId(UUID vendorId) {
        this.vendorId = vendorId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
