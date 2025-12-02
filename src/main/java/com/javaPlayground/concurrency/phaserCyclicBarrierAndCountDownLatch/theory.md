# Theory — Concurrency Synchronizers

## CountDownLatch

* One-time synchronizer
* Waits for N tasks to complete
* `await()` blocks until count reaches 0
* Not reusable

## CyclicBarrier

* Reusable barrier
* All threads wait until all reach the barrier
* Optional barrier action
* Good for multi-step iterative tasks

## Phaser

* Dynamic barrier with phases
* Supports register/deregister
* Multi-step workflow coordination
* Most flexible compared to Latch and Barrier

---

## Strengths, Weaknesses & Best Use Cases

### **CountDownLatch**

**Strengths:**

* Very simple and efficient.
* Ideal when you only need synchronization *once*.
* Low overhead and easy to understand.

**Weaknesses:**

* Not reusable — once count reaches zero, it cannot be reset.
* Cannot support dynamic numbers of parties.

**Best Use Cases:**

* Waiting for several services to initialize before starting the application.
* Waiting for a group of worker threads to finish.
* Triggering an action after N asynchronous tasks complete.

---

### **CyclicBarrier**

**Strengths:**

* Fully reusable — threads can repeatedly meet at the barrier.
* Optional barrier action allows adding logic between phases.
* Great for iterative algorithms.

**Weaknesses:**

* Number of parties is fixed — no dynamic registration.
* If one thread hangs, the barrier may break and throw `BrokenBarrierException`.

**Best Use Cases:**

* Simulations (e.g., multi-step physics engines).
* Iterative algorithms like Jacobi relaxation or k-means clustering.
* Coordinating worker threads performing work in rounds.

---

### **Phaser**

**Strengths:**

* Most flexible of all synchronizers.
* Supports dynamic registration and deregistration.
* Supports hierarchical structures (tree-like phasers).
* Ideal for multi-phase workflows.

**Weaknesses:**

* More complex API compared to latch or barrier.
* Slightly higher overhead.

**Best Use Cases:**

* Complex multi-phase tasks.
* Adaptive parallel computation where number of participants changes.
* Workflow engines where not all tasks participate in each step.

---

## Internal Mechanics (How They Work)

### **CountDownLatch (Internal Behavior)**

* Backed by a `Sync` object using `AbstractQueuedSynchronizer` (AQS).
* Latch count is stored as an atomic integer.
* `countDown()` reduces count; when it reaches zero, all waiting threads are unblocked.
* `await()` uses AQS to park the thread until the state becomes zero.

### **CyclicBarrier (Internal Behavior)**

* Internally tracks:

    * number of parties,
    * number of arrived threads,
    * current “generation”.
* When the last thread arrives:

    * the barrier action (if present) runs,
    * all waiting threads are released,
    * a new generation is created.
* If a thread times out or interrupts:

    * barrier enters “broken” state and all threads are released.

### **Phaser (Internal Behavior)**

* Holds two main fields encoded in one long:

    * phase number,
    * number of registered parties (active participants).
* Supports dynamic changes to party count.
* Each phase completes when all registered parties signal arrival.
* More scalable than CyclicBarrier in many scenarios.

---

## Comparing Latch vs Barrier vs Phaser

### When to Use Which?

| Feature                 | CountDownLatch  | CyclicBarrier    | Phaser                           |
| ----------------------- | --------------- | ---------------- | -------------------------------- |
| Reusable                | ❌ No            | ✔️ Yes           | ✔️ Yes                           |
| Dynamic number of tasks | ❌ No            | ❌ No             | ✔️ Yes                           |
| Multi-phase workflows   | ❌ No            | ⚠️ Limited       | ✔️ Excellent                     |
| Simplicity              | ✔️ Very simple  | ✔️ Moderate      | ❌ Most complex                   |
| Ideal for               | One-time events | Iterative phases | Dynamic multi-phase coordination |

---
