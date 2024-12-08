package com.mathisha.ticketing.Repositories;


import com.mathisha.ticketing.Models.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VendorRepository extends JpaRepository<Vendor, UUID> {
    Optional<Vendor> findByUsername(String username);
    Optional<Vendor> findByEmail(String email);
}
