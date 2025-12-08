# Theory - Deadlocks, Livelocks & Starvation

## 1. Deadlocks

Deadlock occurs when two or more threads permanently block each other while waiting for resources.

### Causes of Deadlock

4 conditions **must all be true**:

1. **Mutual Exclusion** — resources cannot be shared.
2. **Hold and Wait** — thread holds one resource and waits for another.
3. **No Preemption** — locks cannot be forcibly taken.
4. **Circular Wait** — a circular dependency forms.

### Common Deadlock Scenario

Two locks acquired in opposite order.

### Avoiding Deadlocks

* **Enforce lock ordering**
* **Use tryLock() with timeout**
* **Use a single lock instead of multiple when possible**
* **Use higher-level concurrency utilities** (Executors, semaphores)

### Example Strategy: Lock Ordering

Always acquire locks in the same order across the system.

---

## 2. Livelocks

Threads are not blocked, but keep responding to each other and make no progress.

### Example Scenario

Two threads repeatedly yielding/releasing a resource to "help" the other.

### Fix

* Introduce randomness
* Use backoff strategy

---

## 3. Starvation

A thread is forever denied access to CPU or resources.

### Causes

* Unfair locks
* Priority inversion
* Bounded queues filled by fast producers

### Fixes

* Use **fair locks** (`ReentrantLock(true)`)
* Use priority-aware scheduling
* Limit producer rate

---

## 4. Fair vs Unfair Locking

### Fair Lock

* Serves threads FIFO
* Prevents starvation
* Slower (more context switching)

### Unfair Lock (default)

* Faster
* But can starve lower-priority threads

Use fairness only when needed.

---

## 5. Summary

* Deadlock = threads **blocked forever** due to circular waits.
* Livelock = threads **active but not progressing**.
* Starvation = thread **never gets resources**.
* Solutions involve lock discipline, fairness, and timeouts.
