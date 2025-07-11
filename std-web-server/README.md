I do what I think

---

### GRPC Server

#### GRPC Server Configuration Management

#### Enable GRPC mTLS for Transport (!!, internal zero-trust network)

#### GRPC Connection Observer (!!)

- Monitor connection throughput.
- Monitor lifetime of connections.
- Track connected frequency of peer addresses.

#### GRPC Connection Pool

- By default, I use `grpc-netty-shaded` or `grpc-netty` for gRPC server. It handles gRPC HTTP/2.0 connections by itself.
- Netty have a boss group including 1-2 event loop to accept connections and a worker group including
  typically `CPU_cores * 2` event loop to handle connection I/O.

#### GRPC Proto Reflection Service

#### GRPC Call Security (!!)

#### GRPC Call Observer (!!)

- Monitor request throughput & latency (on status, etc.)
- Logging & Tracing (correlation_id from client or auto generated trace_id).

#### GRPC Call Exception Handler (!!)

##                      

---

### HTTP Server

#### HTTP Server Configuration

- Let `spring-boot-starter-web` or `spring-boot-starter-webflux` take care it.
- `spring-boot-starter-web` for Tomcat server
- `spring-boot-starter-webflux` for Netty server

#### GRPC Connection Observer (!!)

#### GRPC Connection Pool (!!, Tomcat only)

#### HTTP Call Security (!!)

#### HTTP Call Observer (!!)

#### HTTP Call Exception Handler

- Using `@RestControllerAdvice`

##                      

---

### API Convention

#### Response

> Object data\
> ErrorInfo error
> > String code <enum ErrorE>\
> > String message [optional]\
> > String params [optional]\
> > String type <enum ErrorType>

#### Data Pagination (!!)

##                      

---

### Utilities

#### JSON Mapper

- Jackson Object Mapper
- Google Protobuf Printer

##                      

---

### Monitor

#### Prometheus

- Counter
- Gauge
- Timer (Histogram & 1-unit Counter)
- Summary

#### Influx (!!)

##                      

---

## TODO

- [ ] Data Pagination for gRPC
- [ ] Client Streaming for gRPC
- [ ] Server Streaming for gRPC
- [ ] BiDirection Streaming for gRPC
- [ ] Security Middleware
- [ ] Authentication & Authenticator
- [ ] Metrics Exporter & Monitor AOP
- [ ] Interceptor & Logging AOP
- [ ] Distributed Tracing Integration
- [ ] Auditing connections
- [ ] Rate-Limiting
- [ ] Deadline / Timeout Management
- [ ] mTLS & Certificate Rotation
- [ ] Custom Request Validation Middleware
- [ ] Role-based Access Control
- [ ] Circuit Breaker
- [ ] Retry & Backoff Logic
- [ ] Idempotency Key Handling (Hybrid approach (Cache + Database))
- [ ] Uniqueness Key Generator (Snowflake ID (due to time-sortable & scalable) + customer-friendly key generator)
- [ ] Graceful Shutdown / Connection Draining
- [ ] Dynamic Configuration Reload
- [ ] Health Check Service
- [ ] @Scheduler and Reactive Scheduler
- [ ] Reactive Hot Stream
- [ ] Practical integration (R2DBC, Kafka, WebClient)
- [ ] HTTP Connection Pool for Tomcat server (handled by Spring Boot)
- [ ] HTTP Connection Observer

- [ ] Unit Tests
- [ ] Profiling
- [ ] Performance Testing