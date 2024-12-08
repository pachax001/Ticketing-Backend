package com.mathisha.ticketing.Controllers;

import com.mathisha.ticketing.DTO.AuthenticationRequest;
import com.mathisha.ticketing.DTO.AuthenticationResponse;
import com.mathisha.ticketing.Models.Customer;
import com.mathisha.ticketing.Models.User;
import com.mathisha.ticketing.Models.Vendor;
import com.mathisha.ticketing.Repositories.CustomerRepository;
import com.mathisha.ticketing.Repositories.VendorRepository;
import com.mathisha.ticketing.Security.JwtUtils;
import com.mathisha.ticketing.Services.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VendorRepository vendorRepository;


    @PostMapping("/customer/register")
    public ResponseEntity<String> registerCustomer(@Valid @RequestBody Customer customer) {

        try {
            User registeredUser = authService.registerCustomer(customer);
            return ResponseEntity.ok("Customer registered successfully with ID: " + registeredUser.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/vendor/register")
    public ResponseEntity<String> registerVendor(@Valid @RequestBody Vendor vendor) {

        try {
            User registeredUser = authService.registerVendor(vendor);
            return ResponseEntity.ok("Vendor registered successfully with ID: " + registeredUser.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest request) {
//        try {
//            String username = request.getUsername();
//            Optional<Customer> customerOptional = customerRepository.findByUsername(username);
//            Optional<Vendor> vendorOptional = vendorRepository.findByUsername(username);
//            if (customerOptional.isEmpty() && vendorOptional.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//
//            }
//
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, request.getPassword())
//
//            );
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//            Roles role;
//            UUID userId;
//            if (userDetails instanceof Customer) {
//                role = ((Customer) userDetails).getRole();
//                userId = ((Customer) userDetails).getId();
//            }
//            else if (userDetails instanceof Vendor) {
//                role = ((Vendor) userDetails).getRole();
//                userId = ((Vendor) userDetails).getId();
//
//            }
//            else {
//                throw new IllegalStateException("Unknown user type");
//            }
//            String jwt = jwtUtils.generateToken(userDetails.getUsername(), role.name());
//            return ResponseEntity.ok(new AuthenticationResponse(jwt,userId));
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }
private ResponseEntity<?> authenticateUser(String username, String password, boolean isCustomer) {
    log.info("Authenticating user with username: {}", username);

    try {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // Retrieve authenticated user details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("User authenticated successfully");
        log.info("User details: {}", userDetails.toString());

        // Generate JWT
        String role = isCustomer ? "CUSTOMER" : "VENDOR";
        String jwt = jwtUtils.generateToken(username, role);

        // Handle customer or vendor-specific details
        if (isCustomer) {
            if (!(userDetails instanceof Customer customer)) {
                log.error("UserDetails is not an instance of Customer");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Unexpected user type");
            }
            return ResponseEntity.ok(new AuthenticationResponse(jwt, customer.getId()));
        } else {
            if (!(userDetails instanceof Vendor vendor)) {
                log.error("UserDetails is not an instance of Vendor");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Unexpected user type");
            }
            return ResponseEntity.ok(new AuthenticationResponse(jwt, vendor.getId()));
        }
    } catch (AuthenticationException e) {
        log.error("Authentication failed for username: {}. Reason: {}", username, e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username or password.");
    }
}

    @PostMapping("/customer/login")
    public ResponseEntity<?> authenticateCustomer(@RequestBody AuthenticationRequest request) {
        Optional<Customer> customerOptional = customerRepository.findByUsername(request.getUsername());
        if (customerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No user found with username: " + request.getUsername());
        }
        return authenticateUser(request.getUsername(), request.getPassword(), true);
    }

    @PostMapping("/vendor/login")
    public ResponseEntity<?> authenticateVendor(@RequestBody AuthenticationRequest request) {
        Optional<Vendor> vendorOptional = vendorRepository.findByUsername(request.getUsername());
        if (vendorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No user found with username: " + request.getUsername());
        }
        return authenticateUser(request.getUsername(), request.getPassword(), false);
    }




}


