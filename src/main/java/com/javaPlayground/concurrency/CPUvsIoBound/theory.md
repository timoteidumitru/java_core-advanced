# Theory — CPU vs I/O Bound Concurrency

## Overview

This stage focuses on **choosing the correct concurrency model based on workload characteristics**. Misclassification leads to wasted resources, poor scalability, and complex designs.

---

## 1. CPU-Bound Concurrency

### What it is

Tasks that spend most of their time **executing instructions**, not waiting.

### Typical examples

* Parsing & transformation
* Encryption / hashing
* Aggregations & calculations
* Image / signal processing

### Core rules

* Threads ≈ CPU cores
* Avoid oversubscription
* Avoid blocking

### Best tools

* Fixed thread pool (size = cores)
* ForkJoinPool (divide & conquer)

### Avoid

* Virtual threads
* Huge thread pools

### Why virtual threads are bad here

Virtual threads still execute on carrier threads. For CPU-heavy work they only add scheduling overhead.

---

## 2. ForkJoin for CPU Work

### When to use

* Recursive tasks
* Uneven workloads
* Divide-and-conquer algorithms

### Strengths

* Work stealing
* Cache-friendly
* Excellent CPU utilization

### Limitations

* Not suitable for blocking I/O

---

## 3. SIMD / Vector API (Advanced)

### Purpose

Process multiple values with a single CPU instruction.

### Best use cases

* Numeric-heavy loops
* Large arrays
* Scientific / financial computation

### Not suitable for

* Branch-heavy logic
* Object graphs

---

## 4. I/O-Bound Concurrency

### What it is

Tasks that spend most of their time **waiting**.

### Typical examples

* File I/O
* Network calls
* Database access
* Messaging systems

### Core rules

* Threads can greatly exceed CPU cores
* Blocking is acceptable

---

## 5. Virtual Threads (Ideal for I/O)

### Why they shine

* Extremely cheap to create
* Blocking does not waste OS threads
* Simple imperative code

### Best use cases

* REST APIs
* File processing
* DB-heavy services

### When not enough

* Extreme throughput
* Streaming backpressure across services

---

## 6. Reactive — When It Still Wins

### Reactive is still useful when:

* Millions of concurrent connections
* Event streaming
* Backpressure across boundaries

### With Loom

Reactive is now **optional**, not mandatory.

---

## 7. Mixed Workloads (Most Systems)

### Pattern

```
I/O → CPU → I/O
```

### Best approach

* Virtual threads for I/O
* Fixed/ForkJoin pool for CPU work

### Why

* Prevents CPU starvation
* Keeps I/O scalable

---

## Decision Table

| Workload Type    | Recommended Model    |
| ---------------- | -------------------- |
| Pure CPU         | Fixed / ForkJoin     |
| Pure I/O         | Virtual Threads      |
| Recursive CPU    | ForkJoin             |
| Batch Processing | Producer–Consumer    |
| ETL Pipelines    | Pipeline Pattern     |
| Hybrid           | Virtual + Fixed Pool |

---

## Common Anti-Patterns

* Using virtual threads for CPU-heavy logic
* Blocking inside event loops
* Reactive for simple CRUD
* Oversized thread pools

---

## Key Takeaway

> Correct concurrency is about **classification**, not complexity.

If the thread is **waiting → Virtual Threads**
If the thread is **working → Fixed / ForkJoin**

---


