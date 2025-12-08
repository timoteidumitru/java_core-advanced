# Diagram - Deadlocks, Livelocks & Starvation 

## Deadlock Cycle (Circular Wait)

```
Thread A ----locks----> Lock 1
   ^                     |
   |                     v
Lock 2 <----locks---- Thread B

Both waiting → no progress.
```

## Deadlock Avoidance with lock ordering

```
Always lock in order: L1 → L2

Thread A: lock(L1) → lock(L2)
Thread B: lock(L1) → lock(L2)

No circular wait.
```

## Livelock Example

```
Thread A: "You go first!" → backs off
Thread B: "No, YOU go first!" → backs off

Both keep backing off → no progress.
```

## Starvation

```
Unfair Lock Queue:

[Fast Thread][Fast Thread][Fast Thread][Slow Thread]

Slow Thread keeps getting skipped → starved.
```

## Fair Lock Behavior

```
FIFO Lock:

[Thread1] → [Thread2] → [Thread3]

Everyone eventually gets the lock.
```
