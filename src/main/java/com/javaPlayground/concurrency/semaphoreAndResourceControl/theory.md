# Theory — Semaphores & Resource Control

## 1. What Is a Semaphore?

A **semaphore** is a concurrency control mechanism that manages access to a shared resource through a fixed number of permits.

* Threads **acquire()** a permit before entering critical work.
* When finished, they **release()** the permit.
* If no permits are available, threads **block** until one is released.

Ideal for **resource bounding**, **rate limiting**, and **throughput control**.

---

## 2. Semaphore Permits

* Controls *how many* threads may run a section of code concurrently.
* Example: `new Semaphore(3)` → max **3 simultaneous tasks**.
* Two modes:

    * **Unfair (default):** fast but no ordering.
    * **Fair:** FIFO ordering for waiting threads.

**Strengths**:

* Simple and fast.
* Great for limiting access to expensive operations.

**Weaknesses**:

* Manual acquire/release can cause deadlocks if used incorrectly.
* Not ideal for complex multi-phase tasks.

---

## 3. Bounded Resource Pools

A common semaphore pattern:

* Use a semaphore to represent the number of available resources.
* Example: A pool of 5 database connections.
* Each `acquire()` takes one connection.
* Each `release()` returns it.

**Best for:**

* Database pools
* Thread-safe object pools
* Limiting concurrent file or network operations

---

## 4. Producer-Consumer With Semaphores

Semaphores can fully implement a producer-consumer model:

* **`emptySlots` semaphore** → how many items can still be produced.
* **`fullSlots` semaphore** → how many items are available to consume.
* Producers `acquire(empty)` then `release(full)`.
* Consumers `acquire(full)` then `release(empty)`.

**Strengths**:

* Lock-free coordination between producers and consumers.

**Weaknesses**:

* More complex than using blocking queues.

---

## 5. Controlling Task Throughput

Semaphores can throttle work execution even with large thread pools.

Example uses:

* Run only N CPU-heavy tasks at once.
* Limit simultaneous HTTP requests.
* Prevent disk or DB saturation.

---

## When to Use Semaphores

**Use when you need:**

* To restrict *how many* threads can perform something.
* To protect a limited-size resource pool.
* To implement producer-consumer without queues.
* To throttle or rate-limit expensive tasks.

**Avoid when:**

* You need reusable phase-based synchronization → use Phaser.
* You need all threads to wait at a barrier → use CyclicBarrier.
* You need one-time countdown synchronization → use CountDownLatch.
