# 🏷️ Price Service

A **Spring Boot 3** microservice that resolves the **applicable price** for a product and brand at a given **UTC instant**.  
It follows **Clean Architecture** and uses **H2** in-memory DB for local development, **OpenAPI (YAML)** for API docs, and **Google Jib** for container images.

---

## ✨ Features

- Query the **applicable price** for a product/brand at a specific date-time (UTC).
- Deterministic selection when multiple prices overlap: **highest priority**, then most recent interval.
- **OpenAPI** served from a **static YAML** (`/openapi.yaml`) — no controller annotations.
- **H2** in-memory schema+seed via `schema.sql` and `data.sql`.
- **Global error handling** with RFC 7807 `ProblemDetail`.
- Ready to **containerize** with **Jib**.

---

## 🏗️ Architecture (Clean)

```
price-service/
 ├─ src/main/java/com/itp/price_service/
 │   ├─ application/              # Use cases / orchestrators
 │   │  └─ GetApplicablePrice.java
 │   ├─ domain/                   # Entities, VOs, domain services
 │   │  ├─ model/Price.java, Money, PriceInterval, BrandId, ProductId
 │   │  ├─ repository/PriceRepository.java
 │   │  └─ service/PriceSelector.java
 │   ├─ infrastructure/           # JPA entities, adapters, config
 │   │  ├─ entity/PriceEntity.java
 │   │  ├─ adapter/repository/SpringDataPriceJpa.java
 │   │  └─ config/ApplicationConfig.java, JacksonUtcConfig.java
 │   └─ presentation/             # REST controllers, exception mapper
 │      ├─ PriceController.java
 │      └─ GlobalExceptionHandler.java
 └─ src/main/resources/
     ├─ application.properties
     ├─ schema.sql
     ├─ data.sql
     └─ static/openapi.yaml
```

**Key idea:** the **domain** and **application** layers don’t depend on Spring. Repositories are **ports**; Spring Data JPA adapters live in **infrastructure**.

---

## 🗄️ Database

- **Engine:** H2 in-memory (dev/testing)
- **Schema:** `src/main/resources/schema.sql`
- **Seed:** `src/main/resources/data.sql`
- **Console:** `http://localhost:8080/h2`

Schema (excerpt):
```sql
CREATE TABLE prices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id   BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    price_list INT    NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date   TIMESTAMP NOT NULL,
    priority   INT NOT NULL,
    price      DECIMAL(10,2) NOT NULL,
    currency   VARCHAR(3) NOT NULL
);
```

---

## 📦 Build & Run

### Prerequisites
- Java **21+**
- Maven **3.9+**
- (Optional) Docker, Docker Hub account if you want to push images

### Local run
```bash
mvn spring-boot:run
```
App runs on: `http://localhost:8080`

### Jar
```bash
mvn clean package
java -jar target/price-service-0.0.1-SNAPSHOT.jar
```

### Container image (Jib)
The project includes **Jib** (no Dockerfile needed). Default target in `pom.xml`:
```xml
<to><image>my-docker-account/${project.artifactId}:s1</image></to>
```
Build & push:
```bash
mvn compile jib:build -Dimage=my-docker-account/price-service:latest
```
Run container:
```bash
docker run -p 8080:8080 my-docker-account/price-service:latest
```

---

## 📘 API (OpenAPI YAML)

- Definition: `src/main/resources/static/openapi.yaml`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Raw YAML: `http://localhost:8080/openapi.yaml`

### Endpoint
`GET /api/prices/applicable`

**Query Params**
- `brandId` *(long, required)*
- `productId` *(long, required)*
- `applicationDate` *(ISO-8601 instant, required)* e.g. `2020-06-14T16:00:00Z`

**Example**
```bash
curl "http://localhost:8080/api/prices/applicable?brandId=1&productId=35455&applicationDate=2020-06-14T16:00:00Z"
```

**Sample Response**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDateUtc": "2020-06-14T15:00:00Z",
  "endDateUtc": "2020-06-14T18:30:00Z",
  "finalPrice": 25.45,
  "currency": "EUR"
}
```

**Errors** (RFC 7807 `ProblemDetail`)
- `404 Not Found` when no price applies
- `400 Bad Request` for invalid query params

---

## 🧠 Selection Logic (Business Rule)

Given `brandId`, `productId`, and `applicationDate (UTC)`:
1. Fetch all price rows where `applicationDate` is **between** `start_date` and `end_date` for that brand & product.
2. Pick the **highest `priority`**; as tiebreakers use **most recent interval** as implemented in the domain comparator.

SQL equivalent:
```sql
SELECT *
FROM prices
WHERE brand_id = :brandId
  AND product_id = :productId
  AND :applicationDate BETWEEN start_date AND end_date
ORDER BY priority DESC, start_date DESC, price_list DESC
LIMIT 1;
```

---

## 🔧 Configuration

`src/main/resources/application.properties` (highlights):
```properties
server.port=8080
spring.datasource.url=jdbc:h2:mem:pricingdb;...;INIT=SET TIME ZONE 'UTC'
spring.jpa.hibernate.ddl-auto=none
spring.jackson.time-zone=UTC
springdoc.swagger-ui.url=/openapi.yaml
springdoc.swagger-ui.path=/swagger-ui.html
spring.h2.console.enabled=true
spring.h2.console.path=/h2
```

All timestamps are treated as **UTC**.

---

## 🧪 Tests

Representative suites:
- `application/GetApplicablePriceTest.java`
- `application/GetApplicablePriceExceptionsTest.java`
- `domain/service/PriceSelectorTest.java`
- `presentation/PriceControllerTest.java`
- `presentation/PriceControllerErrorTest.java`

Run:
```bash
mvn test
```

---

## 📚 Tech Stack

- **Java 21**, **Spring Boot 3.5**
- Spring Web, Spring Validation, Spring Data JPA
- **H2** (runtime), Hibernate
- **springdoc-openapi** (UI + YAML)
- **Google Jib** (container images)
- JUnit 5, Mockito

---

## 👤 Author

**Luis Riobueno**  
GitHub: https://github.com/luisriobueno

---

## 🪪 License

MIT (see `LICENSE` if present)
