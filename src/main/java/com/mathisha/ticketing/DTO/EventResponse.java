package com.mathisha.ticketing.DTO;

import java.util.UUID;

public class EventResponse {
    private UUID id;
    private String title;
    private String description;
    private UUID vendorId;

    // Constructor
    public EventResponse(UUID id, String title, String description, UUID vendorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.vendorId = vendorId;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
}
