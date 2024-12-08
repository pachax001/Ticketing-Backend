package com.mathisha.ticketing.Security;

import com.mathisha.ticketing.Enums.Roles;
import com.mathisha.ticketing.Models.Customer;
import com.mathisha.ticketing.Models.Vendor;
import com.mathisha.ticketing.Repositories.CustomerRepository;
import com.mathisha.ticketing.Repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username).orElse(null);
        Vendor vendor = vendorRepository.findByUsername(username).orElse(null);
        if (customer != null) {
            return  customer;
        }
        else if (vendor != null) {
            return vendor;
        }
        throw new UsernameNotFoundException("User not found with email: " + username);
    }
}


