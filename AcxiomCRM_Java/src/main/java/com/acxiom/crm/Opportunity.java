package com.acxiom.crm;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
public class Opportunity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotBlank public String name;
    public String customerName;
    @Positive(message = "Opportunity amount must be greater than zero")
    public double amount;
    @Min(0) @Max(100)
    public int probability;
    @NotBlank public String stage = "Qualification";
    @FutureOrPresent(message = "Expected close date cannot be in the past")
    public LocalDate expectedCloseDate;
    public String assignedTo = "sales";
    public Opportunity() {}
    public double getWeightedValue() { return amount * probability / 100.0; }
}
