package com.mathisha.ticketing.Security;

import com.mathisha.ticketing.Models.Vendor;
import com.mathisha.ticketing.Repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("vendorDetailsServiceImpl")
@Slf4j
public class VendorDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading vendor by username: {}", username);
        Vendor vendor = vendorRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Vendor not found with username: " + username));
        log.debug("Vendor loaded: {}", vendor.getUsername()); // Avoid logging full object
        return vendor;
    }

}
