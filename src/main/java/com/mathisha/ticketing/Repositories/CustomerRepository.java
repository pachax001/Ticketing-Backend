package com.mathisha.ticketing.Repositories;

import com.mathisha.ticketing.Models.Customer;
import com.mathisha.ticketing.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByEmail(String email);
}
