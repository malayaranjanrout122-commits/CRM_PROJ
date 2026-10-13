# AcxiomCRM — Java Edition

A runnable Java/Spring Boot starter based on the supplied AcxiomCRM project specification.

## Technology
- Java 17
- Spring Boot 3.4
- Spring Web REST API
- Spring Data JPA
- H2 file database
- Jakarta Bean Validation
- Plain HTML/CSS/JavaScript dashboard

## Run in VS Code
1. Install JDK 17 or later and Maven (or use a VS Code Java extension pack).
2. Extract this ZIP and open the `AcxiomCRM_Java` folder in VS Code.
3. In the terminal, run:
   ```bash
   mvn spring-boot:run
   ```
   On Windows, if Maven is not installed, install Maven or open the folder in an IDE with Maven support.
4. Visit `http://localhost:8080`.

The H2 database is stored under `./data`. H2 console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data/acxiomcrm`
- User: `sa`
- Password: leave blank

## API endpoints
- `GET /api/dashboard`
- `GET/POST /api/customers`
- `GET/PUT/DELETE /api/customers/{id}`
- `GET/POST/PUT/DELETE /api/leads` (GET/POST/PUT/DELETE support is partial as documented in controller)
- `GET/POST /api/opportunities`
- `GET/POST /api/followups`
- `GET /api/audit`

## Included
- Dashboard KPI cards and sales pipeline totals
- Customer, lead, opportunity and follow-up forms
- Customer email/phone uniqueness and validation
- Opportunity amount/probability/date validation
- Follow-up date validation
- Audit entries for important create/update/delete operations
- REST API and persistent local H2 database

## Important limitations
This is a student starter, not a production-complete implementation of every PDF requirement. Spring Security login/register/logout, ASP.NET Identity equivalent (Spring Security + password hashing), account lockout, role-based access for Admin/Manager/Sales Executive, CSRF protection, complete CRUD for all entities, API DTOs, and richer reporting should be added before claiming full PDF compliance. Do not deploy this starter publicly without implementing those controls.
