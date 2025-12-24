# Theory - Concurrency Debugging

Concurrency debugging focuses on observing live systems rather than reproducing bugs.

---

## 1. Java Flight Recorder (JFR)

JFR is a low-overhead JVM event recorder.

Captures:
- Thread states
- Lock contention
- Allocation pressure
- Virtual thread pinning

Best for:
- Production diagnostics
- Latency analysis
- Loom visibility

---

## 2. async-profiler

async-profiler is a sampling profiler using OS signals.

Profiles:
- CPU usage
- Allocations
- Lock contention

Produces flame graphs with minimal overhead.

---

## 3. Thread Dump Analysis

Thread dumps provide snapshots of all thread states.

Used to detect:
- Deadlocks
- Starvation
- Thread leaks
- Blocking chains

Often the fastest diagnostic tool.

---

## 4. Virtual Thread Insights

Virtual threads introduce new failure modes:
- Carrier thread pinning
- Synchronized blocking
- Native call blocking

Requires Loom-aware tooling.

---

## 5. Lock Contention Profiling

Lock contention indicates scalability bottlenecks.

Identifies:
- Hot locks
- Long critical sections
- Architectural problems

---

## 6. Flame Graphs

Flame graphs visualize sampled stack traces.

Interpretation:
- Width = cost
- Height = call depth

They make performance problems obvious.

---

## Key Insight

If you cannot see what threads are doing, you are guessing.
