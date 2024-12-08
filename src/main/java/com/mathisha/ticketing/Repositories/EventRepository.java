package com.mathisha.ticketing.Repositories;

import com.mathisha.ticketing.DTO.VendorEventStatsResponse;
import com.mathisha.ticketing.Models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("SELECT new com.mathisha.ticketing.DTO.VendorEventStatsResponse(" +
            "e.id, e.title, e.description, " +
            "(SELECT COUNT(t) FROM Ticket t WHERE t.event.id = e.id AND t.status = 'SOLD_OUT'), " +
            "(SELECT COUNT(t) FROM Ticket t WHERE t.event.id = e.id), " +
            "(SELECT COALESCE(SUM(CAST(t.price AS double)), 0) FROM Ticket t WHERE t.event.id = e.id AND t.status = 'SOLD_OUT')" +
            ") " +
            "FROM Event e WHERE e.vendor.id = :vendorId")
    List<VendorEventStatsResponse> findEventStatsByVendor(@Param("vendorId") UUID vendorId);

    @Modifying
    @Query("DELETE FROM Event e WHERE e.vendor.id = :vendorId AND e.id = :eventId")
    void deleteEventByVendorAndId(@Param("vendorId") UUID vendorId, @Param("eventId") UUID eventId);

    @Query("SELECT e FROM Event e WHERE e.vendor.id = :vendorId")
    List<Event> findEventsByVendor(@Param("vendorId") UUID vendorId);



}

