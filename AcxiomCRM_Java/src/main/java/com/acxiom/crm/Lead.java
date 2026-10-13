package com.acxiom.crm;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
public class Lead {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotBlank @Size(max = 100)
    public String name;
    @Email public String email;
    public String phone;
    public String company;
    public String source = "Website";
    @NotBlank public String status = "New";
    @PositiveOrZero public double expectedValue;
    public String assignedTo = "sales";
    public LocalDate createdAt = LocalDate.now();
    public Lead() {}
}
