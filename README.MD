# Java Playground Project

Welcome to the Java Playground Project! This repository is a curated collection of Java examples, algorithms, data structures, and mini-projects designed to demonstrate and deepen your understanding of various Java programming concepts. The examples span a wide range of topics, from foundational algorithms to advanced Java features introduced in recent versions. Each topic is carefully organized into its respective folder, making it easy to navigate and find examples relevant to your interests or needs.

## Project Structure

The project is organized into the following directories, each dedicated to a specific concept or set of related concepts in Java:

### 1. Algorithms
This directory includes implementations of fundamental algorithms that are crucial for problem-solving and coding interviews. Examples include:

- **Binary Search**: Efficient algorithm for locating an item in a sorted array.
- **Bubble Sort**: Simple comparison-based sorting algorithm.
- **Insertion Sort**: Builds a sorted array one element at a time.
- **Selection Sort**: Another comparison-based sorting algorithm.
- **Dynamic Salary Search**: Example demonstrating dynamic data handling.
- **Find Median**: Algorithm to find the median in a dataset.
- **Find Sum of Two Numbers**: Solves the problem of finding two numbers in an array that sum to a specific target.
- **Element Occurrences**: Counts how often each element appears in a collection.

### 2. Collections
This directory contains examples of Java's Collection Framework, demonstrating how to utilize various collection types:

- **HashMap**: Example of key-value pairs storage and retrieval.
- **HashSet**: Example demonstrating a collection of unique elements.
- **ArrayList**: Dynamic array handling with an example of resizing and array manipulation.

### 3. Comparable and Comparator
This section provides examples that showcase the use of the `Comparable` and `Comparator` interfaces in Java. Learn how to sort and compare custom objects with ease:

- **Comparable Interface**: Natural ordering of custom objects.
- **Comparator Interface**: Custom orderings and multiple sorting criteria.

### 4. Concurrency
Concurrency is a critical aspect of modern Java applications. This folder includes examples illustrating how to manage and control threads and tasks:

- **Runnable and Thread**: Examples of creating and running threads using both `Runnable` and `Thread`.
- **Executors Framework**: Example demonstrating how to manage thread pools and asynchronous tasks using the `ExecutorService`.

### 5. Data Structures
Explore implementations of fundamental data structures, which are key to efficient programming and problem-solving:

- **Linked List**: Implementation and manipulation of singly linked lists.
- **Stack**: Implementation of a stack, including an example of a dynamic stack that automatically grows in size when needed.

### 6. Equals and HashCode
This directory demonstrates the importance of correctly overriding the `equals()` and `hashCode()` methods in Java. Understand the underlying principles and ensure your objects behave predictably in collections:

- **Equals Method**: Properly comparing object contents.
- **HashCode Method**: Ensuring object uniqueness in hash-based collections.

### 7. Fail-Fast and Fail-Safe
Understanding the behavior of collections during iteration is crucial for building robust applications. This section provides:

- **Fail-Fast Iterators**: Example that triggers a `ConcurrentModificationException` when the underlying collection is modified during iteration.
- **Fail-Safe Iterators**: Examples using `ConcurrentHashMap` or `CopyOnWriteArrayList` to safely iterate and modify collections concurrently.

### 8. Functional Interfaces
Functional programming was a major addition in Java 8. This folder contains examples of functional interfaces, which are key to leveraging lambda expressions and the Stream API:

- **Function Interface**: Examples using `Function`, `Predicate`, `Consumer`, and `Supplier` interfaces.
- **Stream API**: Illustrates the use of functional interfaces within Java Streams for data processing.

### 9. Immutable Class
Immutability is a powerful concept that helps ensure thread safety and predictability in your applications. This folder demonstrates how to create truly immutable classes, even when working with mutable objects:

- **Immutable Class Example**: Step-by-step example of creating an immutable class, covering common pitfalls and best practices.

### 10. Multiple Data Source
This is a mini-project that demonstrates how to configure and use multiple data sources in a Spring Boot application. This is particularly useful in enterprise applications where different parts of the system may need to interact with different databases:

- **Spring Boot with Multiple Data Sources**: Complete example using Maven to build and manage dependencies.

### 11. Strings
Strings are fundamental in any Java application. This directory provides examples related to string handling and manipulation:

- **String Creation**: Different ways to create strings and the implications of each.
- **String Immutability**: Demonstrates why strings in Java are immutable and how to work with them effectively.

### 12. Java Batch Processing

#### Key Features:
- **Partitioning**:
    - Dataset divided using `EmployeePartitioner` into manageable chunks (`min-value`, `max-value`) for concurrent execution.
    - Grid size configurable to optimize performance.

- **Multithreading**:
    - Utilized a `TaskExecutor` with thread pooling (`CorePoolSize: 14`, `MaxPoolSize: 16`) for parallel processing.
    - Avoided thread starvation by proper step scoping.

- **Efficient CSV Reading**:
    - Used `FlatFileItemReader` to parse the large CSV file, mapping entries to `EmployeeDTO` objects via `LineMapper`.

- **Data Transformation**:
    - Salary adjustments implemented in `EmployeeProcessor` with precise rounding to two decimal places.
    - Adjustments: +10%, +15%, or +25% based on salary range.

- **Database Writing**:
    - Batched inserts performed by `EmployeeWriter` for efficient persistence into MySQL.

- **Compressed Data Response**:
    - GZIP compression applied to the JSON response in the controller, reducing size from ~16.87MB to ~2.61MB.

#### Performance Metrics:
- **For 1000 Entries**:
    - Without Executors: ~1.82s
    - With Executors: ~1.33s
    - With Partitioning (Grid 16): ~817ms

- **For 100k Entries**:
    - Without Partitioning: ~14.78s
    - With Partitioning: ~7.76s (execution) or ~54.32s (with GZIP compression)
    - Response Size Reduction: 16.87MB → 2.61MB (using GZIP)

## Getting Started

To explore and run the examples in this project, you can clone the repository and open it in your favorite IDE. The project is structured to allow easy navigation between different topics, making it a valuable resource for both learning and reference.

### Prerequisites
- Java 8 or Higher: This project utilizes features introduced in Java 8 and beyond, such as lambda expressions, the Stream API, and more.
- Maven: Required for building and running the Spring Boot project found in the multipleDataSource folder.
- IDE: IntelliJ IDEA, Eclipse, or any other modern Java IDE is recommended for exploring and running the examples.

### License
This project is licensed under the MIT License - see the LICENSE file for details.
