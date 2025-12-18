# Part 2: Idempotency & Message Delivery Guarantees — Diagrams

---

## 1. At-Least-Once Delivery

At-least-once delivery guarantees that a message will be delivered
one or more times. Duplicates are possible and expected.

```
Producer
   │
   ▼
Broker
   │
   ▼
Consumer ──X (crash before commit)
   │
   ▼
Consumer (retry)  ← duplicate delivery
```

---

## 2. Idempotent Handler

An idempotent handler ensures repeated messages do not cause
incorrect state changes.

```
Incoming Message (ID=123)
        │
        ▼
Check Idempotency Store
        │
        ├── ID exists ──▶ SKIP processing
        │
        └── ID new ─────▶ PROCESS
                             │
                             ▼
                     Store ID=123
```

---

## 3. Kafka Partition Concurrency

Kafka guarantees ordering **within a partition**, not across partitions.

```
Partition 0:  A → B → C
Partition 1:  D → E → F

Consumers process partitions in parallel
Ordering is preserved per partition
```

---

## 4. Consumer Commit Pattern

Offsets should be committed **after successful processing**.

```
Poll records
    │
    ▼
Process message
    │
    ├── failure ──▶ no commit → replay
    │
    ▼
Commit offset
```

---

## 5. Outbox Pattern

The outbox pattern solves the dual-write problem.

```
Application Transaction
 ├── Update business table
 └── Insert outbox event
        │
        ▼
Outbox Publisher
        │
        ▼
Message Broker (Kafka)
```

---

## 6. Mental Model Summary

```
Distributed systems assume:
- Failures
- Retries
- Duplicates

Correctness is achieved through:
- Idempotent handlers
- Controlled commits
- Reliable event publishing
```

---

## Key Insight

Duplicates are not bugs.
Systems must be designed to tolerate them safely.
