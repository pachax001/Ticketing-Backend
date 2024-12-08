package com.mathisha.ticketing.Repositories;

import com.mathisha.ticketing.DTO.CustomerEventResponse;
import com.mathisha.ticketing.Enums.TicketStatus;
import com.mathisha.ticketing.Models.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {


    @Query("SELECT t FROM Ticket t WHERE t.event.id = :eventId AND t.status = :status")
    List<Ticket> findStatusOfTicketsForGivenEvent(UUID eventId, TicketStatus status);

    @Query("SELECT new com.mathisha.ticketing.DTO.CustomerEventResponse(" +
            "t.event.id, t.event.title, t.event.description, COUNT(t)) " +
            "FROM Ticket t " +
            "WHERE t.customer.id = :customerId " +
            "GROUP BY t.event.id, t.event.title, t.event.description")
    List<CustomerEventResponse> findCustomerEventsWithTicketCount(@Param("customerId") UUID customerId);


    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.customer.id = :customerId AND t.event.id = :eventId AND t.status = 'SOLD_OUT'")
    long countTicketsByCustomerAndEvent(UUID customerId, UUID eventId);

    @Query("SELECT t FROM Ticket t WHERE t.event.id = :eventId AND t.vendor.id = :vendorId AND t.status = 'AVAILABLE'")
    List<Ticket> findAvailableTicketsByEventAndVendor(@Param("eventId") UUID eventId, @Param("vendorId") UUID vendorId, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Ticket t WHERE t.id IN :ticketIds")
    void deleteTicketsByIds(@Param("ticketIds") List<UUID> ticketIds);

    int countByEventId(UUID eventId);

    int countByEventIdAndStatus(UUID eventId, TicketStatus status);

    Optional<Ticket> findFirstByEventId(UUID eventId);

}
