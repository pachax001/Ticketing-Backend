package com.mathisha.ticketing.DTO;



import java.util.UUID;

public class CustomerEventResponse {
    private UUID eventId;
    private String eventTitle;
    private String eventDescription;
    private long ticketsBought;

    public CustomerEventResponse(UUID eventId, String eventTitle, String eventDescription, long ticketsBought) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.ticketsBought = ticketsBought;
    }

    // Getters and Setters
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

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public long getTicketsBought() {
        return ticketsBought;
    }

    public void setTicketsBought(long ticketsBought) {
        this.ticketsBought = ticketsBought;
    }
}
