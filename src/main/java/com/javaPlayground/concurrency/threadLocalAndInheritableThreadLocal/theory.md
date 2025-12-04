# Theory - ThreadLocal & InheritableThreadLocal

## 1. What Is ThreadLocal?

ThreadLocal provides **per-thread isolated variables**, meaning each thread gets its own independent copy. It removes the need for synchronization because no two threads share the same value.

### Why It Exists

* To avoid **shared mutable state**
* To provide **fast, isolated, thread-confined storage**
* To store contextual information (e.g., user, locale, database connections) without passing them explicitly

### Problems It Solves

* Avoids accidental sharing of non-thread-safe objects
* Eliminates lock contention
* Helps keep APIs clean by not passing context objects everywhere

### Strengths

* Extremely fast (read/write is O(1))
* No synchronization needed
* Simple API
* Great for per-thread caching or context

### Weaknesses

* Memory leaks possible with thread pools
* Hard to debug because state is "hidden"
* Lifecycle management can be tricky

---

## 2. What Is InheritableThreadLocal?

A subclass of ThreadLocal that **copies the parent's value into child threads** at creation time.

### Why It Exists

Some systems create background threads that must inherit contextual information:

* security context
* user session
* trace IDs
* audit data

### Problems It Solves

ThreadLocal alone does *not* propagate to new threads. InheritableThreadLocal ensures child threads continue with the same logical context.

### Strengths

* Automatic propagation to child threads
* Great for logging, auditing, tracing
* Easy to use

### Weaknesses

* Does NOT work with **thread pools** (threads are reused)
* Can still cause memory leaks if not cleared
* Propagates only at **thread creation time**, not later updates

---

## 3. Alternatives

### ✔ Pass context explicitly

Most explicit, safest method.

### ✔ Use frameworks that handle propagation

* Spring Security Context
* Reactor Context
* MicroProfile Context Propagation

### ✔ Use wrapper executors

Manually propagate ThreadLocal into tasks.

---

## 4. Best Use Cases

### ThreadLocal

* Per-thread caching (SimpleDateFormat, database connections)
* Thread-scoped context (request ID, transaction ID)
* Highly optimized financial or scientific computations with isolated state

### InheritableThreadLocal

* Request context → background child thread
* Audit logging and security
* Distributed tracing for child threads (but *not* executor threads)

---

## 5. When NOT To Use ThreadLocal

* Inside **ExecutorServices** without cleanup
* When running unbounded tasks
* When using asynchronous/reactive pipelines

Thread pools reuse threads → stale ThreadLocal values persist.

---

## 6. Common Pitfalls

### ❌ Forgetting to clear the ThreadLocal

This causes memory leaks and incorrect data reuse.

### ❌ Assuming it works with thread pools

It doesn’t. Child threads inherit only once.

### ❌ Using ThreadLocal as a global mutable variable

ThreadLocal is *not* meant to replace proper architecture.

---

## Summary Table

| Feature                     | ThreadLocal                       | InheritableThreadLocal             |
| --------------------------- | --------------------------------- | ---------------------------------- |
| Isolated per-thread state   | ✔                                 | ✔                                  |
| Child threads inherit value | ❌                                 | ✔                                  |
| Works with thread pools     | ❌                                 | ❌                                  |
| Risk of memory leaks        | ✔                                 | ✔✔ (higher)                        |
| Best use case               | per-thread cache, request context | security/audit context propagation |

---

