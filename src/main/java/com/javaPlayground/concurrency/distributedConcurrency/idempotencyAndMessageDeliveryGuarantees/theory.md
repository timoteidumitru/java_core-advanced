# Part 2: Idempotency & Message Delivery Guarantees

---

## 1. Why Idempotency Exists

Distributed systems cannot guarantee exactly-once delivery.
Failures, retries, and rebalances cause message duplication.

Idempotency ensures correctness under retries.

---

## 2. Idempotent Handlers

An idempotent handler produces the same result
no matter how many times it is executed.

Strategies:
- Idempotency keys
- Deduplication tables
- Natural idempotent operations
- Version-based updates

---

## 3. At-Least-Once Delivery

Messages are delivered one or more times.
Duplicates are possible.
Message loss is not.

This is the most common delivery guarantee.

---

## 4. Kafka Partition Concurrency

Kafka guarantees ordering per partition.
Parallelism is achieved via partitions.
Correct keying is critical.

---

## 5. Consumer Commit Patterns

Offsets must be committed only after successful processing.
Manual commits give correctness control.
Incorrect commit timing leads to duplicates or loss.

---

## 6. Outbox Pattern

The outbox pattern solves the dual-write problem
by storing outgoing messages in the database.

A background process publishes events safely.

---

## Key Takeaway

Distributed systems trade simplicity for correctness.
Idempotency is mandatory, not optional.
