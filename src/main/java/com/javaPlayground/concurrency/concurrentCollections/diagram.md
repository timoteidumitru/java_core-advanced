# Concurrent Collections — Visual Diagram

```
                      ┌───────────────────────────┐
                      │   Concurrent Collections  │
                      └──────────────┬────────────┘
                                     │
        ┌────────────────────────────┼──────────────────────────────┐
        │                            │                              │
        ▼                            ▼                              ▼
┌──────────────────┐       ┌─────────────────────┐        ┌─────────────────────────┐
│ ConcurrentHashMap│       │  Concurrent Queues  │        │ CopyOnWrite Collections │
└───────┬──────────┘       └───────────┬─────────┘        └───────────┬─────────────┘
        │                              │                              │
        │                              │                              │
        ▼                              ▼                              ▼
┌────────────────────────┐    ┌───────────────────────────┐     ┌──────────────────────┐
│ Segment-free bucketed  │    │   ConcurrentLinkedQueue   │     │ CopyOnWriteArrayList │
│ hashing, CAS nodes     │    │   FIFO, lock-free         │     │ Copy-on-write reads  │
└────────────────────────┘    └───────────────────────────┘     └──────────────────────┘
                                       │
                                       ▼
                         ┌────────────────────────────┐
                         │     ConcurrentLinkedDeque  │
                         │   Double-ended, lock-free  │
                         └────────────────────────────┘

```

```
 Blocking Queues
─────────────────────────────────────────────────────────

                     ┌───────────────────────────────┐
                     │         BlockingQueue         │
                     └────────────────┬──────────────┘
                                      │
          ┌───────────────────────────┼────────────────────────────┬──────────────────────────────┐
          │                           │                            │                              │
          ▼                           ▼                            ▼                              ▼
┌──────────────────┐    ┌───────────────────────┐     ┌────────────────────────┐     ┌────────────────────────┐
│ArrayBlockingQueue│    │LinkedBlockingQueue    │     │  SynchronousQueue      │     │ PriorityBlockingQueue  │
│ Bounded, array   │    │ Unbounded, linked     │     │ No capacity, handoff   │     │ Priority ordering      │
└─────────────┬────┘    └───────────┬───────────┘     └───────────┬────────────┘     └───────────┬────────────┘
              │                     │                             │                              │
              │                     │                             │                              │
              ▼                     ▼                             ▼                              ▼
      Fixed-size workers   Producer/consumer           Direct handoff between        Priority-based schedulers
                                                       producer ↔ consumer           delayed tasks
```

```
 DelayQueue
──────────────────────────────────────────────────────

               ┌─────────────────────────────────────┐
               │            DelayQueue               │
               │ Orders by delay expiration time     │
               │ Uses Delayed elements               │
               └───────────────────────┬─────────────┘
                                       │
                                       ▼
                         Ideal for scheduled retries,
                         expiring caches, time slots
```
