# Futures, Callables, and CompletionStage — Theory

## 1. Motivation: Why Futures & Callables?

Multithreading with `Thread` and `Runnable` works, but they have a major limitation:

* **Runnable cannot return a value**
* **No direct way to know when the task finishes**

As applications grew more concurrent, Java needed a mechanism to:

* Run computations in background threads
* Retrieve values when those threads finished
* Manage exceptions occurring in async computations
* Chain computations without blocking main thread

This is why **Callable**, **Future**, and later **CompletableFuture / CompletionStage** were created.

---

## 2. Callable — A Task That Returns a Value

### 🔹 What it is

A `Callable<T>` is similar to a Runnable but returns a result and can throw checked exceptions.

```java
public interface Callable<V> {
    V call() throws Exception;
}
```

### 🔹 When It's Used

* When you need results from background tasks
* When tasks might fail and you need exception handling

### 🔹 Why Not Runnable?

Because Runnable: 🔸returns nothing 🔸doesn't throw checked exceptions

---

## 3. Future — A Handle to an Async Computation

A **Future** represents the result of an asynchronous computation.

### Future allows you to:

* **Check if task is done** → `isDone()`
* **Cancel task** → `cancel()`
* **Block until result is ready** → `get()`
* **Wait with timeout** → `get(timeout, unit)`

### But Future has limitations:

* `get()` **blocks** — no async callback
* No way to chain computations
* No streamlined error handling

This paved the way for **CompletableFuture** and **CompletionStage**.

---

## 4. CompletableFuture / CompletionStage

Introduced in **Java 8**, they are the modern, powerful asynchronous programming tools.

### What CompletableFuture adds:

✔ Non-blocking execution (`thenApply`, `thenRun`, `thenCompose`)
✔ Chaining tasks like promises (JavaScript-like)
✔ Handling success & failure cleanly (`exceptionally`)
✔ Combining multiple async tasks (`allOf`, `anyOf`)
✔ Reactive-style async pipelines

### CompletionStage is the interface

`CompletableFuture` is the implementation.

---

## 5. Classification of Concepts

### ✔ Callable

* Represents a computation
* Returns a value
* Blocks only if Future#get is used

### ✔ Future

* Represents the result of a computation
* Allows blocking retrieval
* No composition

### ✔ CompletableFuture / CompletionStage

* Fully async
* Supports chaining & composition
* Does not require manual thread management
* Declarative

---

## 6. Use Case — Real Example

### Scenario: Online Store — Fetch Product, Inventory, Pricing

You need to:

1. Fetch product details
2. Fetch live inventory
3. Fetch discounted price
4. Combine them into a response

Using `CompletableFuture`:

```java
CompletableFuture<Product> productFuture = CompletableFuture.supplyAsync(() -> productService.getProduct(id));
CompletableFuture<Integer> inventoryFuture = CompletableFuture.supplyAsync(() -> inventoryService.getStock(id));
CompletableFuture<Double> priceFuture = CompletableFuture.supplyAsync(() -> pricingService.getDiscountedPrice(id));

CompletableFuture<ProductDetails> result = productFuture
    .thenCombine(inventoryFuture, (product, stock) -> new Temp(product, stock))
    .thenCombine(priceFuture, (tmp, price) -> new ProductDetails(tmp.product, tmp.stock, price));

ProductDetails finalResponse = result.join();
```

### Why this is powerful:

* All calls run **in parallel**
* No blocking inside tasks
* Clear pipeline of async operations
* Easy error handling

---

## 7. Robust Example — Multistage Data Processing Pipeline

Below is a conceptual workflow:

1️⃣ Stage 1 — Read user input asynchronously
2️⃣ Stage 2 — Validate input
3️⃣ Stage 3 — Process the data
4️⃣ Stage 4 — Save to database
5️⃣ Stage 5 — Notify user

Achieved with a CompletableFuture chain:

```java
CompletableFuture.supplyAsync(() -> readData())
    .thenApply(data -> validate(data))
    .thenCompose(valid -> processAsync(valid))  // returns CompletableFuture
    .thenApply(processed -> saveToDb(processed))
    .thenAccept(result -> sendNotification(result))
    .exceptionally(ex -> { log.error("Error: ", ex); return null; });
```

This pipeline is fully asynchronous, non-blocking, and easy to extend.

---

## 8. Error Handling in CompletableFuture

### Approaches:

* `exceptionally()` — recover with fallback value
* `handle()` — transform result even after error
* `whenComplete()` — side-effect logging

Example:

```java
future.exceptionally(ex -> {
    log.error("Failed: " + ex.getMessage());
    return DEFAULT_VALUE;
});
```

---

## 9. Best Practices

### ✔ Do

* Use `supplyAsync()` for tasks returning values
* Use custom Executor for heavy workloads
* Use `thenCompose()` instead of nested futures
* Use `allOf()` to run tasks in parallel

### ❌ Avoid

* Calling `.get()` unless absolutely needed → blocks
* Sharing mutable state across async tasks
* Creating too many threads inside CompletableFuture

---

## 10. Summary

### 🟦 Callable

* Function that returns a result

### 🟧 Future

* Optional handle for async result — blocking

### 🟩 CompletableFuture / CompletionStage

* Full async API
* Task chains, parallelism, error handling
* Best modern method for async programming in Java

---

