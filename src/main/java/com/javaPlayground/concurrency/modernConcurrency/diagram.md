# Visual Diagrams - Modern Concurrency

## 1. Virtual Threads

```
            ┌─────────────────────────────────────────┐
            │         Virtual Thread Model            │
            └─────────────────────────────────────────┘

         Millions of Virtual Threads
        ┌──────┬──────┬──────┬──────┬──────┬──────┐
        │ VT1  │ VT2  │ VT3  │ VT4  │ VT5  │ ...  │
        └──┬───┴──┬───┴──┬───┴──┬───┴──┬───┴──┬───┘
           │       │       │       │       │
           ▼       ▼       ▼       ▼       ▼
   ┌──────────────────────────────────────────────────────┐
   │                 Carrier Threads (few)                │
   └──────────────────────────────────────────────────────┘
```

Virtual threads get mounted/unmounted on a small pool of carrier threads.

---

## 2. Structured Concurrency

```
Parent Task
    ├── Child Task A
    │       └── Subtask A1
    └── Child Task B

If any task fails:
    ⟹ Entire tree is cancelled
```

Hierarchy guarantees no leaked threads.

---

## 3. Flow API (Reactive Streams)

```
 ┌───────────┐    onSubscribe()     ┌──────────────┐
 │ Publisher │──────────────────────▶│ Subscriber   │
 └─────┬─────┘                       └───────┬──────┘
       │   onNext(item)                      │
       │────────────────────────────────────▶
       │                                     │
       │   onComplete() / onError()          │
       │────────────────────────────────────▶
```

Backpressure control:

```
Subscriber ── request(n) ──▶ Publisher
```
