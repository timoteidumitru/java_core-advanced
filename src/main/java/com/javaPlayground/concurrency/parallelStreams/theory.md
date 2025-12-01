# Theory - Parallel Streams

## 8.1 Relationship with ForkJoinPool
- Parallel Streams run on the **common ForkJoinPool** by default.
- You can override the pool using `ForkJoinPool.submit(() -> stream.parallel()...)`.
- Parallel Streams use **RecursiveTasks** internally but abstract away splitting/merging.
- Best suited for **stateless**, **non-blocking**, **CPU‑bound** operations.

---

## 8.2 How Parallel Streams Work
- Data is split into chunks using a **Spliterator**.
- Each chunk is processed in parallel by ForkJoin threads.
- Partial results are combined (reduce-like operations).
- Order-sensitive operations reduce parallel efficiency.

---

## 8.3 Spliterators
### What they do
- Provide controlled splitting for parallel processing.
- Enable efficient partitioning → better parallelism.

### Key methods
- `trySplit()` — return a new Spliterator with half the data.
- `tryAdvance()` — process one element.
- `characteristics()` — define stream behavior (ORDERED, SIZED, IMMUTABLE, etc.)

### When to implement a custom Spliterator
- When working with **non-collection data sources**
  (trees, graphs, generators, IO streams, DB cursors).
- When default splitting is inefficient.

---

## 8.4 Performance Considerations
### When Parallel Streams shine
- CPU‑heavy computations.
- Independent elements.
- Large datasets (> 10,000 items).
- Low allocation per element.

### When they perform worse
- Small datasets (thread overhead > computation).
- IO-bound workloads.
- Stateful operations (e.g., `sorted()`, `distinct()`).
- Ordered operations such as `findFirst()`.

### Tips
- Use `parallel()` only when profiling shows benefit.
- Combine with custom Spliterators for complex structures.
- Ensure work per element is substantial.

---

## 8.5 Stateless vs Stateful Operations
### Stateless Operations
- **map, filter, flatMap**
- Do not store global state.
- Safe for parallel use.
- Highest parallel efficiency.

### Stateful Operations
- **sorted, distinct, limit, skip**
- Require global coordination.
- Reduce or eliminate parallel benefit.
- `limit()` and `findFirst()` force ordering → slow in parallel.

### Rule of thumb
- Prefer **stateless**, **associative**, **order‑independent** operations in parallel streams.
