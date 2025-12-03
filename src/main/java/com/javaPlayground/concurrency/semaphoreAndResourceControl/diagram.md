# Semaphores — Visual Diagram

## 1. Semaphore Permits (Concept)

```
+------------------+
|  Semaphore(3)    |
+------------------+
   ▲     ▲     ▲
   |     |     |
 Permits available
```

Threads must acquire a permit:

```
Thread T1 → acquire → OK
Thread T2 → acquire → OK
Thread T3 → acquire → OK
Thread T4 → acquire → BLOCKED (no permits)
```

---

## 2. Bounded Resource Pool

```
+----------------------------+
|  Resource Pool (size = 2)  |
+----------------------------+
       ▲              ▲
       |              |
   [Resource1]   [Resource2]

Semaphore(2) controls access.
```

Flow:

```
T1 acquire → gets Resource1
T2 acquire → gets Resource2
T3 acquire → BLOCKED
```

When a thread releases:

```
T1 release → T3 wakes and acquires
```

---

## 3. Producer–Consumer With Semaphores

```
   emptySlots (N)          fullSlots (0)
         ▲                       ▲
         |                       |
   Producers               Consumers
```

Flow:

```
Producer: acquire(empty) → produce → release(full)
Consumer: acquire(full)  → consume → release(empty)
```

---

## 4. Throughput Throttling

```
Large Thread Pool (100 threads)
↓
Semaphore(5)
↓
Only 5 tasks run at the same time
```

Effectively limits concurrent workload.
