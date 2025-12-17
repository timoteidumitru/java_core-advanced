# Theory - Distributed Concurrency (Microservices)
## Part 1: Distributed Locks & Leader Election

---

## 1. Context: Why Distributed Concurrency Exists

In a single JVM, concurrency is solved using:
- synchronized
- ReentrantLock
- Atomic variables
- Thread coordination primitives

These mechanisms rely on **shared memory**.

In a distributed system:
- Multiple JVMs
- Multiple hosts
- Network partitions
- Partial failures
- Independent clocks

There is **no shared memory**, only shared state over the network.

Distributed concurrency exists to answer one question:

> How do multiple independent services agree that only ONE of them may perform a task?

---

## 2. What Is a Distributed Lock?

A distributed lock guarantees:
- Mutual exclusion across processes
- Crash recovery
- Correctness under partial failure

Common use cases:
- Scheduled jobs
- Preventing duplicate processing
- Exclusive access to shared resources
- Leader-based coordination

---

## 3. Redis RedLock

### Overview
Redis RedLock is an algorithm that uses:
- Multiple Redis instances
- Time-based leases (TTL)
- Majority quorum

### Strengths
- Very fast
- Simple to implement
- Low operational overhead

### Weaknesses
- Depends on clock accuracy
- TTL may expire mid-operation
- Not strictly linearizable
- Unsafe under certain partitions

### Best Use Cases
- Cache rebuilds
- Rate limiting
- Best-effort locks
- Idempotency guards

### Worst Use Cases
- Financial transactions
- Exactly-once guarantees
- Critical correctness paths

---

## 4. ZooKeeper

### Overview
ZooKeeper is a distributed coordination service providing:
- Strong consistency
- Total ordering
- Ephemeral nodes
- Watches

### Locking Model
- Ephemeral sequential znodes
- Smallest sequence number owns the lock
- Automatic cleanup on client failure

### Strengths
- Strong correctness guarantees
- Automatic crash recovery
- Correct leader election

### Weaknesses
- Higher latency
- Operational complexity
- Requires quorum

### Best Use Cases
- Leader election
- Distributed schedulers
- Master–worker coordination

---

## 5. etcd

### Overview
etcd is a distributed key-value store built on Raft consensus.

Used heavily in:
- Kubernetes
- Cloud-native systems

### Characteristics
- Strong consistency
- Lease-based locks
- Native leader election APIs

### Strengths
- Easier than ZooKeeper
- Cloud-native
- Excellent tooling

### Weaknesses
- Requires quorum
- Not ultra-low latency

---

## 6. Database Advisory Locks

### Overview
Locks provided by the database itself.

Examples:
- PostgreSQL: pg_advisory_lock
- MySQL: GET_LOCK

### Strengths
- Simple
- No extra infrastructure
- Strong consistency

### Weaknesses
- Poor scalability
- Database becomes bottleneck
- Tied to DB availability

### Best Use Cases
- Migrations
- Low-frequency coordination
- Small systems

---

## 7. Leader Election

Leader election ensures:
- Exactly one active leader
- Others remain in standby
- Automatic failover

Common leader tasks:
- Schedulers
- Coordinators
- Cleanup jobs
- Partition assignment

---

## 8. Distributed Lock vs Leader Election

| Aspect | Distributed Lock | Leader Election |
|------|------------------|----------------|
| Duration | Short-lived | Long-lived |
| Ownership | Temporary | Stable |
| Failover | TTL or session | Automatic |
| Purpose | Protect critical section | Control plane |

---

## 9. Comparison Summary

| Mechanism | Consistency | Speed | Complexity | Best For |
|--------|------------|-------|-----------|---------|
| Redis RedLock | Medium | High | Low | Best-effort locking |
| ZooKeeper | Strong | Medium | High | Correct leader election |
| etcd | Strong | Medium | Medium | Cloud-native systems |
| DB Advisory Locks | Strong | Low | Low | Small-scale coordination |

---

## Key Takeaway

Distributed concurrency prioritizes **correctness over performance**.

Local locks protect memory.
Distributed locks protect **system behavior**.
