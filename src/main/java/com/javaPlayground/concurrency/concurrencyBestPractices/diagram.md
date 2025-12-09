# Diagrams - Concurrency Best Practices

## 1. Race Condition (Lost Update) — Visual

```
Time --->
Thread A: read value (v=0) ---------+         write v=1
                                    \       /
                                     \     /
Thread B:         read value (v=0) ----+--- write v=1

Result: both wrote 1 -> final value 1 (lost update)
Expected: 2
```

## 2. Immutable Snapshot Flow

```
Thread A: originalList -> copy -> add -> publish immutable snapshot
Thread B: read immutable snapshot (no locks, safe)

No mutation allowed after snapshot -> safe concurrent reads
```

## 3. Local Variable Safety

```
Thread 1: localX = compute(); // safe, on stack
Thread 2: localX = compute(); // safe, independent

No shared memory -> no synchronization required
```

## 4. Choosing Correct Tool (Decision Flow)

```
Need to share state? -- No -> prefer immutability & stateless services
                \-- Yes -> Is it simple counter? -- Yes -> AtomicInteger/LongAdder
                            \-- No -> Is it collection? -- Yes -> Concurrent Collection
                                            \-- No -> Use locks or redesign
```


