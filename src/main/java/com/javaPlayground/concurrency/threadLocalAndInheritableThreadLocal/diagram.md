ThreadLocal Diagram

====================

## ThreadLocal Behavior

```
            +-----------------+
            |   Main Thread   |
            +-----------------+
                    |
                    |  set(valueA)
                    v
            +-----------------+
            | ThreadLocal Map |
            +-----------------+
                    |
        ------------------------------
        |                            |
        |                            |
        v                            v
+-----------------+       +-----------------+
|    Thread A     |       |    Thread B     |
+-----------------+       +-----------------+
| valueA (local)  |       |  null / valueB  |
+-----------------+       +-----------------+
```

Each thread has its **own isolated copy**.

## InheritableThreadLocal Behavior

```
        +-----------------+
        |   Main Thread   |
        +-----------------+
                |
                |  set(valueX)
                v
      +-------------------------+
      | InheritableThreadLocal |
      +-------------------------+
                |
                |  spawn child thread
                v
        +---------------------+
        |   Child Thread      |
        +---------------------+
        |   inherits valueX   |
        +---------------------+
```

Child thread receives a **copy at creation time**.

```
IMPORTANT: If value changes later in the parent, child does NOT update.
```
