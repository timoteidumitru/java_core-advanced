# Theory for Futures, Callables, CompletableFuture & CompletionStage

---

# 1. Motivation for Asynchronous Programming

Modern applications require:

* Non-blocking I/O
* High throughput under load
* Efficient resource utilization
* Concurrent execution of tasks
* Data processing pipelines

Java provides several abstraction levels for async programming:

* **Future** (old, blocking)
* **Callable** (task with return value)
* **CompletableFuture/CompletionStage** (modern, non-blocking)
* **Reactive Streams (Flux/Mono)** (fully async event-driven pipelines)

---

# 2. Callable & Future (Traditional Model)

## Callable

A task that can return a result and throw exceptions.

```java
Callable<Integer> task = () -> 42;
```

## Future

Represents a pending result.

Characteristics:

* Blocking: `get()` blocks
* No chaining
* No composition
* No non-blocking callbacks

Useful for:

* Simple parallelism
* Short-lived background tasks
* CPU parallel computation

Limitations:

* Hard to orchestrate
* No good error handling model
* No async pipeline support

---

# 3. CompletableFuture & CompletionStage

Introduced in Java 8 to provide **functional** and **non-blocking** asynchronous pipelines.

CompletableFuture enables:

* async tasks (`supplyAsync`, `runAsync`)
* chaining (`thenApply`, `thenCompose`)
* merging (`thenCombine`)
* parallel operations (`allOf`, `anyOf`)
* async error handling (`exceptionally`, `handle`)
* custom executors (`async` overloads)

CompletionStage is the underlying interface describing all async steps.

---

# 4. CompletableFuture Pipeline Categories

CompletableFuture provides several types of transformations.

## 4.1 thenApply (sync mapping)

Transforms a result synchronously.

```java
cf.thenApply(x -> x * 2);
```

## 4.2 thenCompose (async flat-mapping)

Used to chain dependent async tasks.

```java
cf.thenCompose(id -> fetchUser(id));
```

## 4.3 thenCombine (merge independent futures)

```java
cfUser.thenCombine(cfAddress, (u, a) -> new Profile(u, a));
```

## 4.4 allOf / anyOf

Parallel fan-out and fan-in.

* **allOf** → wait for all
* **anyOf** → wait for first

---

# 5. Async Pipelines (Structured Asynchronous Programming)

Async pipelines resemble dataflow graphs:

```
startAsync
    → transform
    → transformAsync
    → compose
    → merge parallel
    → final consumer
```

Advantages:

* Non-blocking execution
* Clear structure
* High scalability
* Manual control over thread pools

---

# 6. Benchmark Comparison (ExecutorService vs CompletableFuture vs Reactor)

## Scenario

1000 simulated remote calls (10ms each).

| Model                   | Ranking    | Notes                                         |
| ----------------------- | ---------- | --------------------------------------------- |
| **Reactor (Flux/Mono)** | 🥇 Fastest | Non-blocking event loop, optimized schedulers |
| **CompletableFuture**   | 🥈 Medium  | Better pipelining, fewer thread hops          |
| **ExecutorService**     | 🥉 Slowest | Blocking, high context switching              |

Reasons:

* CF reduces synchronization and uses async chaining
* Reactor uses event loops and backpressure
* ExecutorService requires manual blocking synchronization

---

# 7. Visual Diagrams

## 7.1 ExecutorService (Thread per task)

```
Task 1 ─────────▶ [Thread]
Task 2 ─────────▶ [Thread]
Task 3 ─────────▶ [Thread]
```

* Blocking
* High context switching
* Limited expressiveness

---

## 7.2 CompletableFuture Pipeline (DAG)

```
supplyAsync
    ├── thenApply
    ├── thenCompose
    └── thenCombine
         └── allOf
```

* Non-blocking
* Easy to build complex async sequences

---

## 7.3 Reactor (Event Loop Model)

```
Flux.range
    → flatMap
    → Scheduler parallel
    → merge
    → final consumer
```

* Highly optimized
* Non-blocking
* Ideal for IO at scale

---

# 8. When to Use What?

| Use Case                            | Best API               |
| ----------------------------------- | ---------------------- |
| Simple background job               | Future/ExecutorService |
| Async pipeline with transformations | CompletableFuture      |
| Massive IO-parallel workloads       | Reactor (Flux/Mono)    |
| Dependent async operations          | thenCompose            |
| Merge parallel results              | thenCombine / allOf    |
| Low-latency backend API             | Reactor                |

---

# 9. Key Best Practices

* Avoid blocking (`get()`, `join()`) unless necessary
* Use custom executors for heavy CPU tasks
* Prefer `thenCompose` over nested futures
* Use `handle` or `exceptionally` for error recovery
* Prefer Reactor for massive IO systems

---

