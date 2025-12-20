# Diagrams - Reactive & Message-Driven Concurrency 

---

## Reactor Model

```
Events → Demultiplexer → Handler → Async Processing
```

---

## Backpressure

```
Consumer ──request(n)──▶ Producer
Producer ──emit n──────▶ Consumer
```

---

## Hot vs Cold

Cold:
```
Subscriber → New Stream
```

Hot:
```
Live Source → Subscriber
```

---

## Reactor vs Loom

```
Reactive → Streams → Backpressure
Loom → Virtual Threads → Blocking
```
