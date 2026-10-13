package com.acxiom.crm;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
public class FollowUp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotBlank public String subject;
    public String relatedTo;
    @NotNull @FutureOrPresent(message = "Follow-up date cannot be earlier than today")
    public LocalDate followUpDate;
    public String type = "Call";
    public String status = "Planned";
    public String assignedTo = "sales";
    public String notes;
    public FollowUp() {}
}
