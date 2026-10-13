package com.acxiom.crm;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CrmController {
    private final CustomerRepository customers;
    private final LeadRepository leads;
    private final OpportunityRepository opportunities;
    private final FollowUpRepository followUps;
    private final AuditLogRepository audit;

    public CrmController(CustomerRepository customers, LeadRepository leads,
                         OpportunityRepository opportunities, FollowUpRepository followUps,
                         AuditLogRepository audit) {
        this.customers = customers; this.leads = leads; this.opportunities = opportunities;
        this.followUps = followUps; this.audit = audit;
    }

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {
        List<Opportunity> open = opportunities.findAll().stream()
                .filter(o -> !Set.of("Won", "Lost").contains(o.stage)).toList();
        return Map.of("customers", customers.count(), "leads", leads.count(),
                "opportunities", opportunities.count(),
                "won", opportunities.findAll().stream().filter(o -> "Won".equals(o.stage)).count(),
                "lost", opportunities.findAll().stream().filter(o -> "Lost".equals(o.stage)).count(),
                "pipeline", open.stream().mapToDouble(o -> o.amount).sum(),
                "weightedPipeline", open.stream().mapToDouble(Opportunity::getWeightedValue).sum(),
                "followUps", followUps.count());
    }

    @GetMapping("/customers") public List<Customer> getCustomers() { return customers.findAll(); }
    @PostMapping("/customers") @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@Valid @RequestBody Customer c) {
        Customer saved = customers.save(c);
        audit.save(new AuditLog("CREATE", "Customer", String.valueOf(saved.id), "Customer created"));
        return saved;
    }
    @GetMapping("/customers/{id}") public Customer getCustomer(@PathVariable Long id) {
        return customers.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/customers/{id}") public Customer updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer c) {
        Customer old = getCustomer(id);
        old.name=c.name; old.email=c.email; old.phone=c.phone; old.company=c.company; old.city=c.city; old.status=c.status;
        Customer saved=customers.save(old);
        audit.save(new AuditLog("UPDATE", "Customer", String.valueOf(id), "Customer updated"));
        return saved;
    }
    @DeleteMapping("/customers/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id) {
        getCustomer(id); customers.deleteById(id);
        audit.save(new AuditLog("DELETE", "Customer", String.valueOf(id), "Customer deleted"));
    }

    @GetMapping("/leads") public List<Lead> getLeads() { return leads.findAll(); }
    @PostMapping("/leads") @ResponseStatus(HttpStatus.CREATED)
    public Lead createLead(@Valid @RequestBody Lead l) {
        Lead saved=leads.save(l); audit.save(new AuditLog("CREATE", "Lead", String.valueOf(saved.id), "Lead created")); return saved;
    }
    @PutMapping("/leads/{id}") public Lead updateLead(@PathVariable Long id, @Valid @RequestBody Lead l) {
        Lead old=leads.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        old.name=l.name; old.email=l.email; old.phone=l.phone; old.company=l.company; old.source=l.source; old.status=l.status; old.expectedValue=l.expectedValue; old.assignedTo=l.assignedTo;
        Lead saved=leads.save(old); audit.save(new AuditLog("UPDATE", "Lead", String.valueOf(id), "Lead updated")); return saved;
    }
    @DeleteMapping("/leads/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLead(@PathVariable Long id) { leads.deleteById(id); audit.save(new AuditLog("DELETE", "Lead", String.valueOf(id), "Lead deleted")); }

    @GetMapping("/opportunities") public List<Opportunity> getOpportunities() { return opportunities.findAll(); }
    @PostMapping("/opportunities") @ResponseStatus(HttpStatus.CREATED)
    public Opportunity createOpportunity(@Valid @RequestBody Opportunity o) {
        Opportunity saved=opportunities.save(o); audit.save(new AuditLog("CREATE", "Opportunity", String.valueOf(saved.id), "Opportunity created")); return saved;
    }
    @GetMapping("/followups") public List<FollowUp> getFollowUps() { return followUps.findAll(); }
    @PostMapping("/followups") @ResponseStatus(HttpStatus.CREATED)
    public FollowUp createFollowUp(@Valid @RequestBody FollowUp f) {
        FollowUp saved=followUps.save(f); audit.save(new AuditLog("CREATE", "FollowUp", String.valueOf(saved.id), "Follow-up scheduled")); return saved;
    }
    @GetMapping("/audit") public List<AuditLog> getAudit() { return audit.findAll(); }
}
