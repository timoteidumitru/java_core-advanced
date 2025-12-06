# Theory - Java Memory Model

## Core Principles

* **Happens-before**: the partial order that guarantees visibility and ordering between actions across threads.
* **Visibility**: when a write by one thread becomes observable by another.
* **Ordering**: compilers/CPUs may reorder instructions unless prevented by happens-before edges.

## Key Happens-before Rules

* **Program order** (a thread's actions happen in program order).
* **Monitor (synchronized)**: unlock() happens-before subsequent lock().
* **Volatile**: write to volatile happens-before subsequent read of same volatile.
* **Thread start**: a thread’s start() happens-before any action in the started thread.
* **Thread join**: all actions in thread T happen-before another thread successfully returns from T.join().
* **Transitivity**: hb is transitive.

## Safe Publication

Safe publication techniques ensure other threads see a fully-initialized object:

* Publish via a `volatile` reference.
* Publish via `synchronized` (lock/unlock).
* Publish through concurrent collections (e.g., `ConcurrentHashMap`).
* Initialize in a static initializer.
* Use final fields for immutable state.

## Final Field Semantics

* Final fields assigned in a constructor (without `this` escaping) are guaranteed to be visible to other threads once the object reference is seen.
* Non-final fields need synchronization or other hb edges for visibility.

## Volatile and Memory Fences

* `volatile` writes and reads create memory fences preventing certain reorderings.
* `synchronized` provides both mutual exclusion and visibility guarantees (unlock publishes, lock acquires).
* Atomic classes provide atomic RMW semantics and visibility.

## Common Patterns & Recipes

* **Use `final`** for immutable state.
* **Use `volatile`** for simple flags and to publish references.
* **Use `synchronized`** or **Atomic** for compound actions.
* **Use concurrent collections** for safe publication of shared references.

## Concise Examples (copy into classes to run)

* **Volatile flag** (visibility): shows why volatile stops a worker loop from spinning forever.
* **Safe vs Unsafe publication**: demonstrates publishing via plain reference vs volatile or concurrent container.
* **Final field semantics**: shows final values visible even without additional synchronization.
* **Double-checked locking**: correct pattern using `volatile` for singletons.

## Practical Takeaways

* If you need visibility across threads, create a happens-before edge.
* Prefer immutable objects and safe publication.
* When correctness matters, prefer `synchronized` or atomics before optimizing.


