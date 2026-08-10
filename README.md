# Architectural Considerations & Design Specifications

## Key Architectural Questions & Considerations
The first thing I would ask is the application's request load, data freshness needs, and the expected average response time. 
Based on that, I would decide which architecture to use, whether caching, pagination, etc., are worthwhile. 
For example, if the product load is very heavy, it would be convenient to create a dedicated microservice exclusively for that 
and another microservice dedicated to reading the products per customer.

Before defining the target architecture, key non-functional requirements must be evaluated:
* **System Load & Throughput:** Expected request volume and concurrent users.
* **Data Freshness Requirements:** Acceptable latency for read operations after a write.
* **Response Time Targets:** Expected SLA for query and upload endpoints.



---

### Data Source & Validation Rules
Clear error-handling policies must be established for incoming data:

* **Strict Strategy:** Reject the entire payload/file if any record fails validation (when business requirements demand 100% data integrity).
* **Fault-Tolerant Strategy:** Accept valid records while recording errors or status flags for invalid ones (when operational tolerance is allowed).

---

### Ingestion Mechanism & Security
How will the file be received? Should it be via FTP or via POST with a CSV file format?
Security? Define security policies, token lifespan, etc.

* **Ingestion Channel:** Determine whether files are received via asynchronous batch processing (FTP/SFTP) or synchronous REST APIs (`POST` with `multipart/form-data`).
* **Security & Authorization:** Define security policies, token lifespans, and access control (e.g., Spring Boot Security integrated with Keycloak).

---

## Technical Assumptions

The solution is implemented using **Hexagonal Architecture (Ports and Adapters)** combined with **Java Modules (JPMS / Multi-Module Maven)** to enforce strict separation between domain logic and external infrastructure.

### Module Breakdown
* **`domain`** — Core business entities, value objects, and domain rules (Zero external framework dependencies).
* **`application`** — Use cases, input/output ports, and orchestration logic.
* **`infrastructure`** — External adapters (Persistence, REST Controllers, DB configurations, HTTP clients).

---

## Functional Requirements & Deliverables

### Requirement 1: Upload Product Inventory
* **Endpoint:** `POST /api/v1/products/upload/{client_code}`
* **Payload:** Accepts a CSV file (`multipart/form-data`) and extracts `client_code` from the URL path.
* **Validation Strategy:**
  * Strict file validation: If any field fails validation, the entire transaction is canceled and a `400 Bad Request` with detailed error descriptions is returned.
  * Product `type` must strictly match allowed categories: `"jewellery"` or `"watch"`.
* **Database Logic:**
  * Products are associated with the provided `client_code`.
  * Upsert strategy: If a product already exists in the database, it is updated; otherwise, a new record is created.
* **Persistence:** Uses an in-memory database (H2) for rapid local development and testing.

---

### Requirement 2: Retrieve Product Inventory by Client
* **Endpoint:** `GET /api/v1/products/{client_code}`
* **Pagination Parameters:** Supports `page` and `size` via query parameters (e.g., `/api/v1/products/CLI-10293?page=0&size=10`).
  * Default `page`: `0`
  * Default `size`: `10`
* **Response Payload Format:**
```text
{
  "content": [
    {"Product1"},
    {"Product2"},
    ...
  ],
  "pageNumber": 0,
  "pageSize": 10,
  "totalElements": 11,
  "totalPages": 2,
  "last": false
}
```
### Requirement 3: Environment Configuration
A single default Spring profile (application.properties) is provided for launching and testing the application locally on localhost.

### Requirement 4: Automated Testing Strategy
Unit tests are implemented across critical layers (Domain models and REST Controllers).

Note: Comprehensive, production-grade test coverage is prioritized after core business rules and infrastructure boundaries are stabilized.
