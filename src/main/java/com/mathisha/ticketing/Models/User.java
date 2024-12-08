package com.mathisha.ticketing.Models;


import com.mathisha.ticketing.Enums.Roles;
import com.mathisha.ticketing.Enums.UserStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


import java.time.LocalDate;

import java.util.UUID;






@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Email
    @NotBlank(message = "Email is mandatory")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @NotBlank(message = "Username is mandatory")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "NIC is mandatory")
    @Column(nullable = false, unique = true)
    private String nic;

    @NotBlank(message = "Password is mandatory")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role = Roles.CUSTOMER;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;


    public User() {}

    public User(String firstName, String lastName, String email, String phoneNumber, String username, String nic, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.nic = nic;
        this.password = password;

    }

      @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = null;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
