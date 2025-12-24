# Concurrency Debugging (Diagrams)

## 1. JVM Observability Pipeline
```
Application Code
    │
    ▼
JVM Runtime
    │
    ├──► Java Flight Recorder → Timeline Events
    ├──► async-profiler → Flame Graphs
    ├──► Thread Dumps → Deadlocks / Blocking
    └──► Lock Statistics → Contention Hotspots
```
---

## 2. Virtual Thread Pinning
```
Virtual Thread
       │
       ├──► Non-blocking I/O → Unpinned ✔
       │
       └──► synchronized / native call
       └──► Carrier Thread Pinned ❌
```
Pinned carriers destroy Loom scalability.

---

## 3. Lock Contention

Thread A ──► LOCK L → RUNNING  
Thread B ──► LOCK L → BLOCKED  
Thread C ──► LOCK L → BLOCKED

Hot locks cause throughput collapse.

---

## 4. Thread Dump Snapshot

RUNNABLE  
BLOCKED  
WAITING  
TIMED_WAITING
```
Used for:
✔ Deadlock detection  
✔ Livelock diagnosis  
❌ Performance profiling
```
---

## 5. Flame Graph Interpretation

Wide Frame   = Hot Path (CPU / Lock Time)  
Narrow Frame = Minor Cost

Time flows bottom → top.

---