package com.mathisha.ticketing.DTO;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class EventRequest {
    @NotBlank(message = "Title is mandatory")
    private String title;

    private String description;

    private UUID vendorId;

    // Getters and Setters
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
