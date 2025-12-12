# Concurrency Pattern Diagrams

Below are conceptual diagrams representing the patterns described.

---

## 1. Advanced Producer–Consumer

```
 ┌──────────┐     put()      ┌─────────────────────┐     take()       ┌───────────┐
 │ Producer │ ────────────▶ │       Queue          │ ──────────────▶ │ Consumer  │
 └──────────┘                │ (Bounded/Unbounded) │                  └───────────┘
                             └─────────────────────┘
```

Backpressure occurs when queue is full.

---

## 2. Multi-Stage Pipeline Pattern

```
 ┌──────────┐   ┌───────────┐   ┌───────────┐   ┌────────────┐
 │ Ingest   │ → │ Transform │ → │ Validate  │ → │ Persist    │
 └──────────┘   └───────────┘   └───────────┘   └────────────┘
```

Each stage can run in parallel on its own worker pool.

---

## 3. Disruptor Pattern (LMAX)

```
          Single Producer                     Single Consumer
        ┌────────────────┐                 ┌──────────────────┐
        │ publish(seq)   │                 │ onEvent(event)   │
        └───────┬────────┘                 └─────────┬────────┘
                │                                    │
        ┌───────▼────────────────────────────────────▼─────────┐
        │                Ring Buffer (Fixed Size)              │
        │  [slot0][slot1][slot2]...[slotN]   (No Locks)        │
        └──────────────────────────────────────────────────────┘
```

Designed for extreme throughput + low latency.

---

## 4. Actor Model / Thread-per-Core

```
 ┌─────────┐     message     ┌─────────┐     message     ┌─────────┐
 │ Actor A │ ──────────────▶ │ Actor B │ ─────────────▶ │ Actor C │
 └─────────┘                 └─────────┘                 └─────────┘

   Each actor has its own mailbox and processes one message at a time.
```

No shared state, no locks.

---

## 5. Throughput Comparison Diagram

```
Throughput (ops/sec)

Disruptor         ████████████████████████████  15M–25M
Virtual Threads   ████████████████              7M–12M
Reactive Streams  ████████████                  4M–7M
Traditional PC    ██████                        2M–4M
```

---

## 6. Architecture Comparison Overview

```
Pattern         Parallelism      Latency      Ease-of-Use     Ideal For
--------------------------------------------------------------------------
PC              Medium           Medium       Easy            Work queues
Pipeline        Medium–High      Medium       Easy            ETL flows
Reactive        High             Medium       Hard            Streaming data
Actors          High             Medium–Low   Medium          Event-driven
Disruptor       Extreme          Ultra-Low    Hard            HFT systems
```

---


