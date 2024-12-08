package com.mathisha.ticketing.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mathisha.ticketing.Enums.Roles;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
public class Customer extends User implements UserDetails {
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    public Customer (){
        super();
        this.setRole(Roles.CUSTOMER);
    }

    public Customer (String firstName, String lastName, String email, String phoneNumber, String username, String nic, String password) {
        super(firstName,lastName,email,phoneNumber,username,nic,password);
        this.setRole(Roles.CUSTOMER);
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + super.getRole().name()));
    }
    @Override
    @Transient
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    @Transient
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true; // Assuming the account is always active
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true; // Assuming the account is not locked
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true; // Assuming credentials are always valid
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return super.getStatus() == com.mathisha.ticketing.Enums.UserStatus.ACTIVE;
    }

}
