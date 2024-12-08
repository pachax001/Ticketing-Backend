package com.mathisha.ticketing.DTO;

import java.util.UUID;

public class VendorEventStatsResponse {
    private UUID eventId;
    private String title;
    private String description;
    private long ticketsSold;
    private long ticketsIssued;
    private double totalIncome;

    public VendorEventStatsResponse(UUID eventId, String title, String description, long ticketsSold, long ticketsIssued, Double totalIncome) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.ticketsSold = ticketsSold;
        this.ticketsIssued = ticketsIssued;
        this.totalIncome = totalIncome == null ? 0.0 : totalIncome; // Handle null incomes
    }

    // Getters and setters
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

    public long getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(long ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public long getTicketsIssued() {
        return ticketsIssued;
    }

    public void setTicketsIssued(long ticketsIssued) {
        this.ticketsIssued = ticketsIssued;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }
}
