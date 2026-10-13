package com.acxiom.crm;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AuditLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String action;
    public String entityName;
    public String recordId;
    public String details;
    public LocalDateTime createdAt = LocalDateTime.now();
    public AuditLog() {}
    public AuditLog(String action, String entityName, String recordId, String details) {
        this.action = action; this.entityName = entityName;
        this.recordId = recordId; this.details = details;
        this.createdAt = LocalDateTime.now();
    }
}
