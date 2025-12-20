# Theory - Reactive & Message-Driven Concurrency 

---

## 1. Context

Reactive concurrency addresses a fundamental problem in distributed systems:
**how to remain responsive under high load, variable latency, and partial failure**.

Traditional thread-based systems struggle because:
- Threads are expensive
- Blocking wastes resources
- Backpressure is implicit or absent

Reactive systems make **asynchrony, flow control, and failure explicit**.

---

## 2. Reactor Model

The Reactor model is an **event-driven concurrency pattern** using:
- Event loops
- Non-blocking handlers
- Callback-based execution

**Strengths**
- Massive concurrency
- Low thread usage

**Weaknesses**
- Higher complexity
- Debugging difficulty

---

## 3. Backpressure

Backpressure allows consumers to signal demand explicitly.

Reactive Streams define:
- request(n)
- onNext
- onComplete / onError

Prevents overload at the cost of complexity.

---

## 4. Hot vs Cold Streams

Cold:
- Per-subscriber execution
- Replayable

Hot:
- Continuous emission
- Shared among subscribers

---

## 5. Scheduler Selection

Schedulers control execution context:
- parallel() → CPU
- boundedElastic() → blocking IO
- single() → serialization

---

## 6. Reactor vs Loom

Reactor:
- Non-blocking
- Backpressure-first

Loom:
- Virtual threads
- Blocking-friendly

They solve different problems.

---

## 7. Summary

Reactive systems scale exceptionally well for event-driven workloads.
