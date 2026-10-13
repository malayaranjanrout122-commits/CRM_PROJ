package com.acxiom.crm;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotBlank @Size(max = 100)
    public String name;
    @NotBlank @Email @Column(unique = true)
    public String email;
    @NotBlank @Pattern(regexp = "^[0-9+() -]{10,16}$", message = "Enter a valid phone number")
    @Column(unique = true)
    public String phone;
    public String company;
    public String city;
    public String status = "Active";
    public String assignedTo = "sales";
    public LocalDateTime createdAt = LocalDateTime.now();

    public Customer() {}
}
