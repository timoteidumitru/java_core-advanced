# Theory - Concurrency Testing

Concurrency bugs are fundamentally different from functional bugs.
They depend on timing, interleavings, and memory visibility, making them
non-deterministic and often impossible to reproduce.

---

## 1. JUnit Concurrency Techniques

JUnit concurrency tests explicitly create and coordinate multiple threads using:
- ExecutorService
- CountDownLatch
- CyclicBarrier
- Future

These tests validate:
- Thread safety
- Race condition resistance
- Correct synchronization boundaries
- Visibility guarantees

Strengths:
- Easy to integrate
- Good for validating contracts

Weaknesses:
- Timing-sensitive
- May miss rare interleavings

---

## 2. Stress Testing

Stress testing executes concurrent code repeatedly and aggressively.

Characteristics:
- High thread counts
- High iteration counts
- Long execution times

Reveals:
- Rare race conditions
- Lock contention
- Memory leaks
- Throughput collapse

Best for:
- Queues
- Caches
- Thread pools
- Concurrent collections

---

## 3. Fuzz Testing

Fuzz testing introduces randomness into concurrency tests.

Randomized elements:
- Operation order
- Thread scheduling
- Delays
- Input data

Goal:
- Discover unexpected interleavings
- Trigger non-obvious bugs

Trade-off:
- Excellent bug discovery
- Difficult reproduction

---

## 4. jcstress — Java Memory Model Testing

jcstress is a specialized OpenJDK tool for testing correctness under the Java Memory Model.

It validates:
- Instruction reordering
- Visibility guarantees
- Atomicity assumptions

Core concepts:
- Actors (threads)
- Outcomes
- Forbidden states

Best for:
- Lock-free algorithms
- Volatile semantics
- CAS-based code

---

## 5. Testing Cancellation & Timeouts

Cancellation is a first-class concurrency concern.

Tests must ensure:
- Threads respond to interruption
- Resources are released
- Partial work does not corrupt state

Common failures:
- Swallowed InterruptedException
- Hung threads
- Leaked locks or connections

---

## Key Insight

A concurrency test that never fails is probably not testing concurrency.
