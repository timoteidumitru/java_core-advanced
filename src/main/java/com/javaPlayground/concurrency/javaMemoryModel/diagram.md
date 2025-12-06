# Diagram - Java Memory Model

## 1. Happens-Before Edges (Simple)

```
Thread A: write x
          unlock monitor M
                     \  (happens-before)
Thread B: lock monitor M
          read x
```

Meaning: A's write becomes visible to B after the monitor unlock/lock pair.

## 2. Volatile Visibility

```
Thread A: write volatile v  (flushes stores)
           ...
Thread B: read volatile v   (sees effects before this read)
```

Volatile creates a cross-thread ordering edge.

## 3. Safe Publication Flow

Unsafe publish:

```
Thread A: construct obj (this escapes) -> write reference r to shared non-volatile variable
Thread B: read r -> may see partially-initialized fields
```

Safe publish (via volatile):

```
Thread A: construct obj -> volatileRef = obj  (hb)
Thread B: obj = volatileRef -> sees fully-initialized final fields
```

Or publish via ConcurrentHashMap.put(key, obj) which provides safe publication.

## 4. Final Field Semantics

```
Constructor sets final f
Constructor completes
Reference published safely
Other thread reads reference -> guaranteed to see f's initialized value
```

## 5. Reordering Illustration (No HB)

```
Thread1: A; B;
Thread2: C; D;
Without happens-before, CPU/JIT may reorder A/B and C/D as long as thread-local semantics are preserved, producing surprising cross-thread results.
```

## 6. Memory Fences Overview

* volatile write -> store-store + store-load fences
* volatile read  -> load-load + load-store fences (architecture dependent)
* synchronized unlock -> publish
* atomic ops -> full-fence semantics on many platforms

