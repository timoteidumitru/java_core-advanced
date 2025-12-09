# Theory - Concurrency Best Practices

## 1. Immutable Design

* Prefer immutable objects: all fields `final`, no setters, no mutation after construction.
* Benefits: inherently thread-safe, easy reasoning, safe sharing across threads.
* Use cases: DTOs, configuration objects, event payloads.

**Pattern**: create immutable snapshots; use builders for complex objects.

---

## 2. Stateless Services

* Services should be stateless where possible: behavior depends only on input arguments.
* Benefits: single instance can be shared across threads (scales), simpler testing.
* Example: pure functions, idempotent handlers.

---

## 3. Avoiding Shared Mutable State

* Prefer local variables (stack) instead of shared fields.
* When sharing is necessary, use:

    * `Atomic*` classes (AtomicInteger, LongAdder)
    * `volatile` for simple flags
    * `synchronized` or `ReentrantLock` for compound actions
    * Concurrent collections (ConcurrentHashMap, ConcurrentLinkedQueue)
    * Immutable snapshots or CopyOnWrite collections for read-mostly workloads

**Pitfalls**: hidden state (ThreadLocal), leaking mutable objects, forgetting to clear thread-bound state.

---

## 4. Choosing the Correct Concurrency Construct

* **Use immutability first.** If you must share state, choose the simplest correct tool.
* **Atomic classes**: counters, simple RMW operations.
* **Concurrent collections**: shared maps/queues without external locks.
* **Locks (`synchronized`, `ReentrantLock`)**: use for compound updates, prefer short critical sections.
* **Executors**: manage threads via pools, prefer higher-level abstractions over raw Thread creation.
* **Synchronizers**: Latch/Barrier/Phaser for coordination patterns.
* **Semaphores**: for bounding access to limited resources.

**Rule of thumb**: pick the tool that matches the problem (visibility, atomicity, ordering, or coordination).

---

## 5. Patterns & Anti-Patterns

### Patterns

* Immutable snapshots for sharing
* Local-first variables with single hand-off
* Thread-safe builders and factories
* Use long-lived thread pools (Executors) with bounded queues

### Anti-patterns

* Sharing mutable collections (ArrayList) without synchronization
* Relying on `Thread.sleep()` or I/O side-effects for synchronization
* Using ThreadLocal without cleanup in pooled threads

---

## 6. Testing & Verification

* Increase contention in tests (many threads, many iterations). Add sleeps/yields to amplify race windows.
* Use tools: FindBugs/SpotBugs, concurrency linters, thread analyzers.
* Prefer deterministic unit-tests for correctness (use synchronized or atomic variants in tests).

---

## 7. Quick Checklist Before Shipping

* Are your shared objects immutable or thread-safe?
* Are critical sections minimal and well documented?
* Are resources bounded (queues, connections)?
* Are ThreadLocal cleared in pooled threads?
* Have you run stress tests under contention?

---
