# Visual Diagram

## CountDownLatch — One-Way Gate

```
Workers (do tasks)                    Waiting Thread
----------------------------------    -------------------------
 Task A ---- countDown()  ----\
 Task B ---- countDown()  -----+-->  await() ----> continues
 Task C ---- countDown()  ----/

Initial count = 3
When count reaches 0 → All waiting threads are released
Latch CANNOT be reset
```

```
[3] → 2 → 1 → 0 → RELEASE
```

## CyclicBarrier — Reusable Meeting Point

```
Thread 1 ---- arrives ---\
Thread 2 ---- arrives ----+-- all reached? --> YES → run barrier action → RELEASE
Thread 3 ---- arrives ---/

Barrier resets automatically → ready for next cycle
```

```
   Cycle 1                    Cycle 2
 T1 --\                        T1 --\
 T2 ----> [ Barrier ] ---->    T2 ----> [ Barrier ] ---->
 T3 --/                        T3 --/
```

## Phaser — Multi-Phase, Dynamic, Most Flexible

```
Phase 0:
  T1 --- arrive & await
  T2 --- arrive & await
  T3 --- arrive & await
→ All arrived → advance to phase 1

Phase 1:
  T1 --- arrive & await
  T2 --- arrive & await
  (T3 deregisters)
→ All arrived → advance to phase 2
```

```
        Phase 0                  Phase 1                Phase 2
Register T1                      T1 arrives             T1 arrives
Register T2      ----->         T2 arrives  ----->     T2 arrives → advance
Register T3                      T3 deregisters
       ↑
Threads may join/leave at runtime
```

## Visual Comparison

```
CountDownLatch:
   One-way:  [3] → 2 → 1 → 0 → RELEASE

CyclicBarrier:
   Round-based:  (meet) → release → reset → (meet) → release

Phaser:
   Multi-phase pipeline:
      Phase 0 → Phase 1 → Phase 2 → ... (with dynamic participants)
```
