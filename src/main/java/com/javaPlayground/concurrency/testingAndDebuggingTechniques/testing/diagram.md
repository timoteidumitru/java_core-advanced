# Stage 14 — Concurrency Testing (Diagrams)

## 1. JUnit Concurrency Testing

```
Thread A ──┐
Thread B ──┼──► Shared Mutable State ──► Race / Visibility Bug
Thread C ──┘
```
✔ Correct approach:
- CountDownLatch / CyclicBarrier
- Deterministic start & finish

❌ Bad approach:
- Thread.sleep()
- Timing assumptions

---

## 2. Stress Testing
```
High Thread Count
        │
        ▼
Millions of Operations
        │
        ▼
Rare Timing Window
        │
        ▼
Hidden Concurrency Failure
```
Purpose: amplify probability of failure.

---

## 3. Fuzz Testing

```
Random Delay
Random Cancellation
Random Scheduling
        │
        ▼
Unexpected Interleavings
        │
        ▼
Previously Unknown Bugs
```
Goal: explore state space you didn’t think about.

---

## 4. jcstress (JMM Correctness)

Actor 1 ──► write x = 1  
Actor 2 ──► read x

Observed Outcomes:
✔ Allowed by JMM  
❌ Forbidden by JMM

Enumerates all possible executions.

---

## 5. Cancellation & Timeouts
```
Task Running
│
├──► Interrupt
│       └──► Cleanup → Resource Released
│
└──► Timeout
└──► Forced Cancellation
```

---