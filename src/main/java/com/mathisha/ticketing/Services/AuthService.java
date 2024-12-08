package com.mathisha.ticketing.Services;

import com.mathisha.ticketing.Enums.Roles;
import com.mathisha.ticketing.Enums.UserStatus;
import com.mathisha.ticketing.Exceptions.FieldExistException;
import com.mathisha.ticketing.Models.Customer;
import com.mathisha.ticketing.Models.User;
import com.mathisha.ticketing.Models.Vendor;
import com.mathisha.ticketing.Repositories.CustomerRepository;
import com.mathisha.ticketing.Repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Customer registerCustomer(Customer customer) {
        // Check if email or username already exists
        Optional<Customer> existingUserByEmail = customerRepository.findByEmail(customer.getEmail());
        Optional<Customer> existingUserByUsername = customerRepository.findByUsername(customer.getUsername());

        if (existingUserByEmail.isPresent()) {
            throw new FieldExistException("Email is already registered.");
        }

        if (existingUserByUsername.isPresent()) {
            throw new FieldExistException("Username is already taken.");
        }

        String hashedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(hashedPassword);
        // Set default fields
        customer.setRole(Roles.CUSTOMER);
        customer.setStatus(UserStatus.ACTIVE);

        // Save to database
        return customerRepository.save(customer);
    }

    public Vendor registerVendor(Vendor vendor) {
        // Check if email or username already exists
        Optional<Vendor> existingUserByEmail = vendorRepository.findByEmail(vendor.getEmail());
        Optional<Vendor> existingUserByUsername = vendorRepository.findByUsername(vendor.getUsername());

        if (existingUserByEmail.isPresent()) {
            throw new FieldExistException("Email is already registered.");
        }

        if (existingUserByUsername.isPresent()) {
            throw new FieldExistException("Username is already taken.");
        }

        String hashedPassword = passwordEncoder.encode(vendor.getPassword());
        vendor.setPassword(hashedPassword);
        // Set default fields
        vendor.setRole(Roles.VENDOR);
        vendor.setStatus(UserStatus.ACTIVE);

        // Save to database
        return vendorRepository.save(vendor);
    }
}
