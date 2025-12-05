# Theory - Concurrent Collections

Java's `java.util.concurrent` package provides thread-safe, scalable collections designed for high‑concurrency environments. They avoid the performance bottlenecks of synchronized collections by using non‑blocking algorithms, fine‑grained locks, and specialized data structures.

---

## 1. ConcurrentHashMap

### **What it is**

A highly concurrent, lock-partitioned (striped) hash map.

### **How it works**

* Uses lock striping → multiple threads update different buckets safely.
* Retrievals are non‑blocking.
* Write operations use fine‑grained locking or CAS (depending on JDK version).

### **Best use cases**

* Caches
* Counters & frequency maps
* Shared configuration maps

### **Strengths**

* Extremely fast under high contention
* Lock-free reads
* Atomic operations (`compute`, `merge`, `putIfAbsent`)

### **Weaknesses**

* No deterministic ordering
* Some operations still require locking

---

## 2. ConcurrentLinkedQueue / ConcurrentLinkedDeque

### **What they are**

Lock-free FIFO queues/deques based on CAS operations.

### **How they work**

* Uses non-blocking linked nodes
* Producers/consumers operate using CAS head/tail updates

### **Best use cases**

* Task dispatching
* Event queues
* High-throughput systems

### **Strengths**

* Fast, non-blocking
* Highly scalable

### **Weaknesses**

* Unbounded → can grow until OOM
* Polling when empty wastes CPU (no blocking)

---

## 3. Blocking Queues

Blocking queues coordinate producers and consumers.

### ### LinkedBlockingQueue

* Linked-list based
* Potentially unbounded (optional capacity)
* Classic producer-consumer pattern

### ### ArrayBlockingQueue

* Fixed-size ring buffer
* Predictable memory footprint
* Applies backpressure when full

### ### SynchronousQueue

* Zero capacity
* Direct handoff
* `put()` blocks until `take()` occurs

### ### PriorityBlockingQueue

* Thread-safe priority queue
* Unbounded
* Not fair (largest or smallest element dominates)

### **Blocking queues — strengths**

* Naturally synchronize producers & consumers
* Built-in blocking semantics

### **Blocking queues — weaknesses**

* Some are unbounded → possible memory growth
* PriorityBlockingQueue can cause starvation

---

## 4. CopyOnWrite Collections

(`CopyOnWriteArrayList`, `CopyOnWriteArraySet`)

### **How they work**

* Every mutation creates a new underlying array
* Iterators operate over immutable snapshots

### **Best use cases**

* Event listener lists
* Read-mostly datasets

### **Strengths**

* Safe to modify while iterating
* No locking for reads

### **Weaknesses**

* Very expensive writes
* High memory usage during updates

---

## 5. DelayQueue

### **What it is**

A scheduling queue where elements become available only after a delay.

### **Use cases**

* Expiring cache entries
* Retry scheduling
* Timed tasks without a scheduler

---

## Summary Table

| Collection            | Locking Model        | Best For              | Notes                             |
| --------------------- | -------------------- | --------------------- | --------------------------------- |
| ConcurrentHashMap     | Striped locks + CAS  | Shared maps, caches   | Fastest shared map implementation |
| ConcurrentLinkedQueue | Lock-free CAS        | Event/task queues     | Non-blocking, unbounded           |
| LinkedBlockingQueue   | Blocking             | Producer-consumer     | Most common                       |
| ArrayBlockingQueue    | Blocking, bounded    | Throughput control    | Predictable behavior              |
| SynchronousQueue      | Handoff only         | Thread rendezvous     | Zero-capacity queue               |
| CopyOnWriteArrayList  | Copy-on-write        | Read-mostly workloads | Expensive writes                  |
| DelayQueue            | Delayed availability | Expiring tasks        | Requires `Delayed` items          |

---
