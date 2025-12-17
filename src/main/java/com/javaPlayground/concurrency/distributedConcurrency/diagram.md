# Distributed Concurrency (Microservices)
## Part 1: Distributed Locks & Leader Election — Diagrams

---

## 1. Distributed Lock — Conceptual Overview

A distributed lock ensures that **only one node** in a distributed system
may execute a critical section at any given time.

Local locks protect memory.
Distributed locks protect **system behavior**.

```
┌─────────────┐
│ Service A   │
└──────┬──────┘
       │ tries to acquire lock
┌──────▼──────┐
│ Shared      │  (Redis / ZooKeeper / etcd / DB)
│ Coordinator │
└──────┬──────┘
       │ lock granted
┌──────▼──────┐
│ Service B   │  (blocked / waiting)
└─────────────┘
```

---

## 2. Redis RedLock — Quorum-Based Locking

```
Client
  │
  ├─ SET lock → Redis A ✅
  ├─ SET lock → Redis B ✅
  ├─ SET lock → Redis C ❌
  │
Majority acquired → LOCK GRANTED
```

**Characteristics**
- Fast
- TTL-based
- Best-effort correctness
- Vulnerable to clock drift

---

## 3. ZooKeeper — Ephemeral Sequential Locking

```
/locks
 ├── lock-0001  ← owner
 ├── lock-0002
 └── lock-0003
```

**Characteristics**
- Strong consistency
- Automatic crash cleanup
- Correct leader election

---

## 4. etcd — Raft-Based Locking & Leader Election

```
┌──────────┐
│ Client A │───lease───┐
└──────────┘           │
                    ┌──▼──┐
                    │ etcd│ (Raft quorum)
                    └──┬──┘
                       │
               Leader elected
```

**Characteristics**
- Strong consistency
- Cloud-native
- Kubernetes-friendly

---

## 5. Database Advisory Locks

```
Service A ──┐
            ├─ DB Advisory Lock ── Critical Section
Service B ──┘        (blocked)
```

**Characteristics**
- Simple
- Strong consistency
- Poor scalability

---

## 6. Leader Election — Control Plane Pattern

```
┌─────────┐     heartbeat     ┌─────────┐
│ Leader  │ ───────────────▶ │ Follower│
└─────────┘                   └─────────┘
      │
      └── performs scheduled / coordination tasks
```

---

## 7. Mental Model Summary

```
Single JVM:
  Threads → Locks → Shared Memory

Distributed System:
  Nodes → Consensus → Shared State
```

---

## Key Insight

Distributed concurrency is not about performance.
It is about **correctness under failure**.
