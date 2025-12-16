# Diagrams — CPU vs I/O Bound Concurrency

---

## 1. CPU-Bound Model

```
CPU Core 1 ─┐
CPU Core 2 ─┼─ Fixed / ForkJoin Pool
CPU Core 3 ─┤
CPU Core N ─┘

• Threads ≈ cores
• No blocking
• Maximize computation
```

---

## 2. ForkJoin (Divide & Conquer)

```
          Task
           │
     ┌─────┴──────┐
   Subtask     Subtask
      │            │
   Subtask      Subtask

• Work stealing
• Uneven load balancing
```

---

## 3. I/O-Bound with Virtual Threads

```
     Client Requests
            │
            ▼
┌─────────────────────────┐
│  Virtual Thread Pool    │
│  (10k+ threads)         │
└─────────────────────────┘
      │
      ▼
 Blocking I/O (DB / HTTP)

• Threads wait cheaply
• No OS thread exhaustion
```

---

## 4. Reactive (Extreme I/O)

```
Event Loop
   │
   ▼
 Non-blocking callbacks
   │
   ▼
 Backpressure Signals

• High complexity
• Maximum scalability
```

---

## 5. Mixed Workload (Recommended Hybrid)

```
Virtual Thread
     │  (I/O)
     ▼
 CPU Pool (Fixed / ForkJoin)
     │  (Compute)
     ▼
Virtual Thread
     │  (I/O)
     ▼
 Storage / Network
```

---

## 6. Mental Decision Flow

```
Is the task waiting?
      │
      ├─ YES → Virtual Threads
      │
      └─ NO  → CPU-bound
              │
              ├─ Recursive? → ForkJoin
              └─ Simple?   → Fixed Pool
```

---

## 7. Performance Intuition

```
CPU-bound throughput:
███████████████████  Fixed / ForkJoin
█████████████        Virtual Threads

I/O-bound scalability:
███████████████████  Virtual Threads
███████              Fixed Pool
```

---

## Final Insight

Concurrency success comes from **matching the model to the workload**, not from using the most advanced tool.
