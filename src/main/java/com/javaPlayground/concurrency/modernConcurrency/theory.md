# Theory - Modern Concurrency (Java 21+)

## 1. Virtual Threads (Project Loom)

Virtual threads are lightweight, user-mode threads designed to make high-concurrency applications easier and more scalable.

### Key Ideas

* Extremely cheap to create (millions possible)
* Scheduled by the JVM, not the OS
* Ideal for I/O-bound workloads

### Strengths

* Massive scalability
* Simpler code than async callbacks
* No need for complex thread pools

### Weaknesses

* Not faster for CPU-heavy workloads
* Still susceptible to blocking native calls

### Best Use Cases

* HTTP servers
* Database query dispatchers
* High-throughput I/O systems

---

## 2. Structured Concurrency

A programming model that treats *groups of tasks* as a single unit of work.

### Why It Matters

* Avoids leaked / forgotten threads
* Makes concurrency easier to reason about
* Child tasks automatically cancel on failure

### Strengths

* Safer lifecycle management
* Clean, hierarchical structure
* Automatic cancellation

### Weaknesses

* Requires Java 21+
* Limited advanced customization (compared to full reactive frameworks)

### Best Use Cases

* Performing multiple dependent operations
* Concurrent I/O calls
* Fork-join style queries (user + orders, etc.)

---

## 3. Flow API (Reactive Streams)

Java's built-in reactive model supporting async, non-blocking, backpressure-aware pipelines.

### Concepts

* **Publisher**: emits items
* **Subscriber**: consumes items
* **Subscription**: controls backpressure
* **Processor**: both publisher + subscriber

### Strengths

* Backpressure built-in
* Asynchronous and non-blocking
* Standardized SPI compatible with other reactive libs (Reactor, Akka, RxJava)

### Weaknesses

* Verbose to implement custom publishers
* Not as full-featured as modern reactive libraries

### Best Use Cases

* Event-driven data streams
* Async processing pipelines
* Integrating reactive systems using the standard Java API

---

# Summary Table

| Feature                | Strength                              | Weakness                      | Best Use               |
| ---------------------- | ------------------------------------- | ----------------------------- | ---------------------- |
| Virtual Threads        | High scalability, simple model        | Not ideal for CPU-heavy tasks | Massive I/O workloads  |
| Structured Concurrency | Clean task scoping, safe cancellation | Requires Java 21+             | Multi-call workflows   |
| Flow API               | Backpressure, async streams           | Verbose, low-level            | Event-driven pipelines |


---